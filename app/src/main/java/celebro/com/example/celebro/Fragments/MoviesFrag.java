package celebro.com.example.celebro.Fragments;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import celebro.com.example.celebro.Adapters.MovieGridAdapter;
import celebro.com.example.celebro.BuildConfig;
import celebro.com.example.celebro.Models.MovieItem;
import celebro.com.example.celebro.R;
import celebro.com.example.celebro.Utils.Constants;
import celebro.com.example.celebro.Utils.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFrag extends Fragment {


    final static String LOG_TAG = FetchMovieData.class.getSimpleName();
    private GridView mMoviesGridView;

    private String mSortmethod;

    private ProgressBar mProgressBar;

    private ImageView emptyScreenIcon;

    private View mEmptyScreen;

    private TextView emptyScreenText;

    private TextView mTryAgainButton;

    private final String MOVIES_KEY = "movies";

    private Bundle mBundle;

    private MovieGridAdapter mMovieGridAdapter;

    private List<MovieItem> loadedMovies = new ArrayList<>();

    private boolean firstLoad = true;

    private boolean completed;

    public static MoviesFrag newInstance()
    {
        return new MoviesFrag();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mMovieGridAdapter = new MovieGridAdapter(getActivity());

       mSortmethod = Utility.getPreferredSortingOrder(getActivity());

        if (savedInstanceState != null) {
            updateMovie();

            mBundle = savedInstanceState.getParcelable(MOVIES_KEY);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        String sortMethod = Utility.getPreferredSortingOrder(getActivity());

        if ((sortMethod != null && !sortMethod.equals(mSortmethod))){
        if (mBundle != null)
        {
            restartLoader(mBundle);
        }
    }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = (ProgressBar) rootview.findViewById(R.id.loading_spinner);

        mEmptyScreen = rootview.findViewById(R.id.error_screen);


        emptyScreenText = (TextView) mEmptyScreen.findViewById(R.id.error_message);


        emptyScreenIcon = (ImageView) mEmptyScreen.findViewById(R.id.error_icon);

        mTryAgainButton = (TextView) mEmptyScreen.findViewById(R.id.try_again);

        mMoviesGridView = (GridView) rootview.findViewById(R.id.Grid_view_item);

        mMoviesGridView.setAdapter(mMovieGridAdapter);

        mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem Movie = (MovieItem) mMovieGridAdapter.getItem(position);

                ((OnMovieSelected) getActivity()).onMovieSelected(position, Movie);

            }
        });

        if (savedInstanceState != null) {
            mMovieGridAdapter.swapData(loadedMovies);
        }


        return rootview;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIES_KEY, (ArrayList<MovieItem>) loadedMovies);
    }

    public void restartLoader(Bundle bundle) {
        loadedMovies = bundle.getParcelableArrayList(MOVIES_KEY);
        if (loadedMovies != null) {
            mMovieGridAdapter.swapData(loadedMovies);
        } else {
            this.mBundle = bundle;
        }
        updateMovie();
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }


    private void updateMovie() {
        FetchMovieData MovieTask = new FetchMovieData();

       String sortMethod = Utility.getPreferredSortingOrder(getActivity());
        MovieTask.execute(sortMethod);
    }

    /**
     * Created by 502575517 on 2/13/2016.
     */
    public class FetchMovieData extends AsyncTask<String, Void, List<MovieItem>> {


        //JSON Response Keys
        private static final String ID = "id";
        public static final String POPULARITY = "popularity";
        private static final String POSTER_PATH = "poster_path";
        private static final String TITLE = "title";
        private static final String OVERVIEW = "overview";
        private static final String RELEASE_DATE = "release_date";
        private static final String BACKDROP_PATH = "backdrop_path";
        public static final String VOTE_COUNT = "vote_count";
        public static final String USER_RATING = "vote_average";

        final String MDB_RESULT = "results";


        /*
        * @param context
        *
        * */

        public FetchMovieData() {
                   }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<MovieItem> movieItems) {
            completed = true;
            String emptyText = "";

            if (movieItems != null) {
//                mMovieGridAdapter.cleanData();
                mMovieGridAdapter.swapData(movieItems);
                crossfade(mMoviesGridView, mProgressBar);

            } else {
                emptyText = getResources().getString(R.string.error_no_connection);
                customizeEmptyScreen(R.drawable.no_movie, emptyText, false);

            }

            firstLoad = false;
        }


        @Override
        protected List<MovieItem> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
//            will contain  the raw JSOn response as a string
            String MovieJsonStr = null;

            try {
                final String QUERY_PARAM = "sort_by";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIEDB_API_KEY)
                        .build();
                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                MovieJsonStr = buffer.toString();
                // Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);
            } catch (IOException e) {
                //   Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        //     Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return loadedMovies(MovieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error closing Stream", e);

            }

            return new ArrayList<>();
        }

        /**
         * add the date from the json object to our list
         */


        public List<MovieItem> loadedMovies(String response) throws JSONException {
            loadedMovies = new ArrayList<>();
            JSONObject jsonResponse;

            jsonResponse = new JSONObject(response);
            JSONArray results = jsonResponse.getJSONArray(MDB_RESULT);

            for (int i = 0; i < results.length(); i++) {
                JSONObject singleMovie = results.getJSONObject(i);

//                 Get the ID of the Movie
                int id = singleMovie.getInt(ID);
//                Get the  Title of the Movie
                String title = singleMovie.getString(TITLE);
//                Get the Movie Overview
                String overview = singleMovie.getString(OVERVIEW);
//                Get the release Date
                String releaseDate = singleMovie.getString(RELEASE_DATE);
//                Get the Poster Path for the Image
                String posterPath = singleMovie.getString(POSTER_PATH);
//                Get backDrop Path
                String backDropPAth = singleMovie.getString(BACKDROP_PATH);
//                Get Popularity
                String popularity = singleMovie.getString(POPULARITY);
//                 Get VoteCount
                String VoteCount = singleMovie.getString(VOTE_COUNT);
//                Get the UserRAting
                float userRating = singleMovie.getInt(USER_RATING);

                MovieItem movie = new MovieItem(id, title, posterPath, overview, releaseDate, popularity, VoteCount, backDropPAth, userRating);
                loadedMovies.add(movie);
            }
            return loadedMovies;
        }
    }

    public interface OnMovieSelected {
        void onMovieSelected(int moviePosition, MovieItem movie);
    }

    private void crossfade(final View mContentView, final View mLoadingView) {

        mContentView.setVisibility(View.VISIBLE);

        mLoadingView.setVisibility(View.GONE);

    }

    public void customizeEmptyScreen(int showingIcon, String text, boolean canRetry) {

        emptyScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), showingIcon));

        emptyScreenText.setText(text);

        if (!canRetry) {
            mTryAgainButton.setVisibility(View.GONE);
        } else {
            mTryAgainButton.setVisibility(View.VISIBLE);
        }
    }
}


