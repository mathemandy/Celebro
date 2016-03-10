package celebro.com.example.celebro;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.List;

import celebro.com.example.celebro.Adapters.movieSpinnerAdapter;
import celebro.com.example.celebro.Fragments.MoviesFrag;
import celebro.com.example.celebro.Models.MovieItem;
import celebro.com.example.celebro.Utils.SettingsActivity;
import celebro.com.example.celebro.Utils.Utility;

public class MainActivity extends AppCompatActivity implements MoviesFrag.OnMovieSelected {

    private String THUMB_FRAG_TAG;

    public static final String QUERY_TYPE_FAVORITED = "favorited";

    public static final String QUERY_TYPE_KEY = "key";

    View mFragContainer;

    private Fragment thumbnailsFragment;

    private Toolbar mToolBar;

    private Spinner mQuerySpinner;

    private MovieItem mSelectedMovie;

    private String sortingOrder;

    private List<String> movieDropdown;

    private movieSpinnerAdapter spinnerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sortingOrder = Utility.getPreferredSortingOrder(this);

        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setTitle(getTitle());
        mToolBar.setNavigationIcon(R.drawable.ic_action_navigation_menu);



//        mQuerySpinner = (Spinner) findViewById(R.id.query_spinner);
//
//        String[] items = getResources().getStringArray(R.array.movie_query_array);
//
//        movieDropdown = new ArrayList<>();
//
//        for (int i = 0; i < items.length; i++){
//            movieDropdown.add(items[i]);
//        }
//
////        spinnerAdapter = new movieSpinnerAdapter(actionbar.getThemedContext(), movieDropdown);
//
//        mQuerySpinner.setAdapter(spinnerAdapter);
//
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
//        {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                mQuerySpinner.setDropDownVerticalOffset(-116);
//            }
//        }
//
//        mQuerySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                String queryType = "";
//                switch (position) {
//                    case 0:
//                        queryType = Constants.POPULAR_DESC_PARAMETER;
//                        break;
//                    case 1:
//                        queryType = Constants.VOTED_DESC;
//                        break;
//                    case 2:
//                        queryType = Constants.RELEASE_DATE;
//                }

//                Bundle bundle = new Bundle();

//                bundle.putString(QUERY_TYPE_KEY, queryType);

//                ((MoviesFrag) thumbnailsFragment).restartLoader(bundle);

//            }

//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
        mFragContainer = (FrameLayout) findViewById(R.id.container);

        THUMB_FRAG_TAG = getResources().getString(R.string.thumbnail_frag_tag);

        thumbnailsFragment = MoviesFrag.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, thumbnailsFragment, THUMB_FRAG_TAG).commit();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(int moviePosition, MovieItem movie) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        mSelectedMovie = movie;

        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

//        if (isTwoPane) {
////            send data to the other Fragment
//            Log.i("MainActivity", "Movie Selected and title is: " + mSelectedMovie.getTitle());
////            setMovieHeaderDetails(movie);
////            ((MovieDetails) movieDetailsFragment.loadMovieDetails(bundle));
//        } else {

    }
}
