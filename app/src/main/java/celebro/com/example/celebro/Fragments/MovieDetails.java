package celebro.com.example.celebro.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import celebro.com.example.celebro.BuildConfig;
import celebro.com.example.celebro.Models.MovieItem;
import celebro.com.example.celebro.R;
import celebro.com.example.celebro.Utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetails extends Fragment {
    //    UI Fields
    private TextView movieDirector;

    private TextView movieDuration;

    private TextView movieReleaseDate;

    private TextView movieGenres;

    private TextView movieSysnopsis;

    private ProgressBar mProgressBar;

    private View mDetailsLayout;

    private View trailers;

    private ListView mTrailerList;

    private View cast;

    private ListView mCastList;

    private ImageView mEmptyIcon;

    private TextView mErrorMessage;

    private TextView mErrorDescription;

    private TextView mTryAgain;

    private View emptyLayout;
//    other Variables


    private boolean loadedSuccessfully = true;

    private Bundle bundle;

    private OnMovieFavourited mMovieFavouritedCallback;


    private boolean isFirstLoad = true;

//    MovieHandler movieHandler;

    private MovieItem movie;

    private final int LOADER_ID = 2;


    public static MovieDetails newInstance() {
        return new MovieDetails();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mMovieFavouritedCallback = (OnMovieFavourited) activity;
        } catch (Exception exception) {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();

        if (bundle != null) {

        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
            isFirstLoad = bundle.getBoolean("isFirstLoad");
            Log.i("MovieDetailsFrag", "Is first loaded from state: " + isFirstLoad);
        }
        initFields(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_details_screen, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(Constants.MOVIE_KEY, movie);

        outState.putBoolean("isFirstLoad", isFirstLoad);

        Log.i("MovieDetailsFrag", "IS firstLoaded " + isFirstLoad);
    }

    public interface OnMovieFavourited {
        public void onMovieFavourite(boolean isFavourite);
    }

    public void initFields(View view) {

        //Initialise Empty Layout

        emptyLayout = view.findViewById(R.id.error_screen);

        mEmptyIcon = (ImageView) emptyLayout.findViewById(R.id.error_icon);

        mErrorMessage = (TextView) emptyLayout.findViewById(R.id.error_message);

        mTryAgain = (TextView) emptyLayout.findViewById(R.id.try_again);

        mErrorDescription = (TextView) emptyLayout.findViewById(R.id.error_desc);

        mTryAgain.setVisibility(View.INVISIBLE);

        //progress Bar
        mProgressBar = (ProgressBar) view.findViewById(R.id.loading_spinner);

//        Init Details Layout
        mDetailsLayout = view.findViewById(R.id.layout_details);

        movieDirector = (TextView) mDetailsLayout.findViewById(R.id.movie_director);

        movieDuration = (TextView) mDetailsLayout.findViewById(R.id.movie_duration);

        movieReleaseDate = (TextView) mDetailsLayout.findViewById(R.id.movie_releaseDate);

        movieGenres = (TextView) mDetailsLayout.findViewById(R.id.movie_genres);

        movieSysnopsis = (TextView) mDetailsLayout.findViewById(R.id.plot_synopsis);


        //Inialise the trailers ui
        trailers = mDetailsLayout.findViewById(R.id.trailers);

        mTrailerList = (ListView) trailers.findViewById(R.id.list);

        mTrailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mYoutubeID = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent((Intent.ACTION_VIEW), Uri.parse("vnd.youtube:" + mYoutubeID));
                intent.putExtra("VIDEO_ID", mYoutubeID);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Youtube App not Found, please install it before watching this video", Toast.LENGTH_LONG).show();
                }
            }
        });
        mTrailerList.setEmptyView(trailers.findViewById(android.R.id.empty));

        //Init cast layoutn
        cast = mDetailsLayout.findViewById(R.id.cast);
        mCastList = (ListView) mDetailsLayout.findViewById(R.id.list);

        if (bundle != null) {
            movie = bundle.getParcelable(Constants.MOVIE_KEY);
            if (movie != null) {
                fillData(movie);
                crossfade(mDetailsLayout, mProgressBar);
            } else {
                customizeEmptyScreen(R.drawable.no_movie, "Please Select a movie on Your Left");
                crossfade(emptyLayout, mProgressBar);

            }
        } else {
            customizeEmptyScreen(R.drawable.no_movie, "Please Select a movie on Your Left");
            crossfade(emptyLayout, mProgressBar);

        }
    }

    public void customizeEmptyScreen(int icon, String text) {
        mEmptyIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), icon));

        mErrorMessage.setText("");

        mErrorDescription.setText(text);
    }

    public void fillData(MovieItem movie) {
        movieReleaseDate.setText(movie.getReleaseDate());
        movieSysnopsis.setText(movie.getOverview());
    }

    private void crossfade(final View mContentView, final View mLoading) {

        mContentView.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
    }


    public class MovieDetailsLoader extends AsyncTask<Void, Void, List<MovieItem>> {

        private MovieItem receivedMovie;
        private Context mContext;

//         fields
        private static final String RUNTIME = "runtime";
        public static final String GENRES_KEY = "genre";
        public static final String GENRE_NAME_KEY = "name";
        public static final String TRAILERS_KEY = "youtube";
        public static final String YOUTUBE_KEY = "trailers"

        public MovieDetailsLoader(Context context, MovieItem movie) {
            mContext = context;
            this.receivedMovie = movie;
        }

        @Override
        protected List<MovieItem> doInBackground(Void... params) {

            String requestUrl = Constants.SINGLE_MOVIE_BASE_URL + receivedMovie.getId() + "?api_key=" + BuildConfig.MOVIEDB_API_KEY + Constants.FIELDS_TO_APPEND;

            try {
                receivedMovie = addDetailsToMovie(loadMovieDetails(requestUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return (List<MovieItem>) receivedMovie;
        }

        public String loadMovieDetails(String url) throws IOException {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response res = client.newCall(request).execute();

            return res.body().toString();
        }
//         public  MovieItem addDetailsToMovie(String response) {
//             MovieItem movie = this.receivedMovie;
//             JSONObject jsonresponse;
//
//         }
    }



