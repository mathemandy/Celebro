package celebro.com.example.celebro;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import celebro.com.example.celebro.Fragments.MovieDetails;
import celebro.com.example.celebro.Models.MovieItem;
import celebro.com.example.celebro.Utils.Constants;

public class DetailsActivity extends AppCompatActivity {

    private String FRAGMENT_TAG;

    private MovieItem movie;

    private Toolbar mToolbar;

    private Fragment fragment;

    private View mHeaderLayout;

    private ImageView headerImageView;

    private ProgressBar spinner;

    private ImageView imageView;

    private TextView maxRating;

    private TextView mCurrentRating;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Target target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        Bundle bundle = getIntent().getExtras();

        FRAGMENT_TAG = getResources().getString(R.string.movie_details_fragment_tag);

        movie = (MovieItem) bundle.getParcelable("movie");

        configureToolbar();

        fragment = MovieDetails.newInstance();

        fragment.setArguments(bundle);

        setFragment(fragment);

    }

    public void configureToolbar() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);


        collapsingToolbarLayout.setTitle(movie.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getApplication(), getColor(R.color.colorCollapsingToolBarText)));
        } else {
            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorCollapsingToolBarText));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), getColor(R.color.colorCollapsingToolBarText)));
        } else {
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorCollapsingToolBarText));
        }

        mHeaderLayout = findViewById(R.id.headerLayout);

        spinner = (ProgressBar) mHeaderLayout.findViewById(R.id.movie_item_spinner);

        headerImageView = (ImageView) mHeaderLayout.findViewById(R.id.header);

        String backDropUrl = Constants.BACKDROP_URL_BASE_PATH + movie.getBackDropPath();
        Picasso.with(getApplicationContext()).load(backDropUrl).into(target);

       target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                headerImageView.setImageBitmap(bitmap);
                spinner.setVisibility(View.GONE);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                headerImageView.setBackgroundResource(R.color.colorCollapsingToolBarText);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                headerImageView.setBackgroundResource(R.color.colorCollapsingToolBarText);
                spinner.setVisibility(View.VISIBLE);

            }
        };
/**
 *
 * Setup the Rating
 * */

        mCurrentRating = (TextView) mHeaderLayout.findViewById(R.id.current_rating);

        maxRating = (TextView) mHeaderLayout.findViewById(R.id.max_rating);

        float rating = movie.getUserRating();

        if (rating > 9) {
            maxRating.setVisibility(View.GONE);
        } else {
            if (maxRating.getVisibility() != View.VISIBLE) {
                maxRating.setVisibility(View.VISIBLE);
            }
        }
        mCurrentRating.setText(Float.toString(rating));

/**
 *
 * Setup the naviagation toolBar to go back
 * */


        mToolbar = (Toolbar) collapsingToolbarLayout.findViewById(R.id.toolbar2);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                                                  }
                                              }
        );

    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.details_container, fragment, FRAGMENT_TAG).commit();
    }


}
