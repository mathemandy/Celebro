package celebro.com.example.celebro;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.Grid_view_item);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        return rootView;
    }

    public class FetchMovieDb extends AsyncTask<String, Void, String[]>{
        private final String LOG_TAG = FetchMovieDb.class.getSimpleName();


        @Override
        protected String[]  doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
//            will contain the raw JSON response as a string.
            String MovieJSONStr = null;

            try {
                //Construct the URL for the MovieDb query
                //Possible parameters are available at the moviedb API page,
//                https://www.themoviedb.org/documentation/api

                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String QUERY_PARAM = "sort_by";
                final String APPID_PARAM = "api_key";

                Uri   builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIEDB_API_KEY)
                        .build();
                URL url  = new URL(builtUri.toString());
                Log.v(LOG_TAG,"Built URI " + builtUri.toString());

                //create the request to MovieDb Website, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

//                Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null){
//                    NOthing to do
                    return  null;

            }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
//                    Since it's JSON, adding a new line isnt  necessary(it wont be affected)
//                    But it does make debugging a *lot easier if you print out the completed
//                    buffer for debugging.
                    buffer.append(line + "\n");

                    if (buffer.length() == 0){
                        //Stream was empty. No point in parsing
                        return  null;
                    }
                    MovieJSONStr = buffer.toString();
                    Log.v(LOG_TAG, "Popular Movie String: " + MovieJSONStr );
                                    }
            } catch (IOException e){
                  Log.e(LOG_TAG, "Error ", e);
//                 If the code didn't successfully get the weather data, there's no point in attemping
//                 to parse it.
             return  null;
                     }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch
                            (final  IOException e){
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson (MovieJSONStr);
            }
            catch (JSONException e)
            {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;

        }
    }

    private String[] getMovieDataFromJson(String movieJSONStr) throws JSONException {

//        These are the names of the JSON Objects that need to be extracted
        final String
        return new String[0];
    }
}
