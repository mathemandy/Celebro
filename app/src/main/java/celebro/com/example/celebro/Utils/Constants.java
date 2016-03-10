package celebro.com.example.celebro.Utils;

/**
 * Created by 502575517 on 2/11/2016.
 */
public class Constants {
    public static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

    public static final String SINGLE_MOVIE_BASE_URL="https://api.themoviedb.org/3/movie/";

    public static final String FIELDS_TO_APPEND="&append_to_response=genres,trailers,credits,reviews";

    public static final String POPULAR_DESC_PARAMETER = "popularity.desc";

    public static final String VOTED_DESC="vote_average.desc";

    public static final String RELEASE_DATE="release_date.desc";

    public static final String THUMB_URL_BASE_PATH="http://image.tmdb.org/t/p/w185//";

    public final static String MOVIE_KEY = "movie";

    public static final String BACKDROP_URL_BASE_PATH="http://image.tmdb.org/t/p/w500/";
}
