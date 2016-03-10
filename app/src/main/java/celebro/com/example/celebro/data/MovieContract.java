package celebro.com.example.celebro.data;

import android.provider.BaseColumns;

/**
 * Created by 502575517 on 3/8/2016.
 */
public class MovieContract {

    public interface  MovieColumns
    {
        String MOVIE_TITLE = "title";

        String Movie_ID = "id";

        String MOVIE_OVERVIEW = "overview";

        String RELEASE_DATE = "release_date";

        String GENRES = "genres";

        String BACKDROP_PATH = "backdrop_path";

        String POSTER_PATH = "poster_path";

        String DIRECTOR =  "director";

        String USER_RATING = "user_rating";

        String RUNTIME = "runtime";

        String IMPORT_HASH_KEY = "import_hash";
    }

    public interface CastColumns
    {

        String ACTOR_NAME = "actor_name";

        String PROFILE_URL = "profile_url";

        String CHARACTER_NAME = "character_name";

        String IMPORT_HASH_key = "import_hash";

        String MOVIE_ID = "id";

    }

    public interface TrailerColumns
    {
        String NAME = "trailer_name";

        String  MOVIE_ID = "MOVIE_ID";

        String YOUTUBE_ID = "youtubeID";

        String IMPORT_HASH_KEY = "import_hash";

    }

    public static final class MovieEntry implements BaseColumns{

    }
}
