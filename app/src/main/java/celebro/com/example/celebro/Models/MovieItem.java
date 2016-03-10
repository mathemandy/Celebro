package celebro.com.example.celebro.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import celebro.com.example.celebro.Adapters.TrailerAdapter;
import celebro.com.example.celebro.Utils.HashUtils;

/**
 * Created by 502575517 on 2/8/2016.
 */
public class MovieItem implements Parcelable {


    private int id;
    private String posterPath;
    private String director;
    private String overview;
    private String releaseDate;
    private String popularity;
    private String backdropPath;
    private String title;
    private String voteCount;
    private float userRating;
    private int runtime;
    private boolean isFavourited;
    private List<TrailerAdapter> trailers = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private List<CastItem> cast = new ArrayList<>();
    private List<String> reviews = new ArrayList<>();


    public MovieItem() {
        this.id = 0;
        this.title = "unavailable";
        this.posterPath = "unavailable";
        this.popularity = "unavailable";
        this.voteCount = "unavailable";
        this.overview = "unavailable";
        this.releaseDate = "unavailable";
        this.backdropPath = "unavailable";
        this.userRating = 0;
        this.isFavourited = false;


    }

    public MovieItem(int movieId,
                     String title,
                     String moviePoster,
                     String overview,
                     String releaseDate,
                     String popularity,
                     String voteCount,
                     String backdropPath,
                     float userRating
    ) {
        this.id = movieId;
        this.title = title;
        this.posterPath = moviePoster;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
        this.userRating = userRating;
        this.isFavourited = false;

    }

    public String getImportedhasCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("id").append(id == 0 ? "" : id)
                .append("director").append(director == null ? "" : director)
                .append("title").append(title == null ? "" : title)
                .append("overview").append(overview == null ? "" : overview)
                .append("releaseDate").append(releaseDate == null ? "" : releaseDate)
                .append("posterPath").append(posterPath == null ? "" : posterPath)
                .append("backdrop").append(backdropPath == null ? "" : backdropPath);


        return HashUtils.computeWeakHash(builder.toString());

    }

    /**
     * Getters and Setters
     */
    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews.add(reviews);
    }

    public List<CastItem> getCast() {
        return cast;
    }

    public void setCast(CastItem cast) {
        this.cast.add(cast);
    }

    public List<String> getGenres() {

        return genres;
    }

    public String setGenres(String genres) {
        String genre = "";

        if (getGenres().size() > 0) {
            for (int i = 0; i < getGenres().size(); i++) {

                genre = genre + " " + getGenres().get(i);
                if (i == 2) break;

            }
        } else {
            genre = "N/A";

        }
        return genre;
    }

    public List<TrailerAdapter> getTrailers() {
        return trailers;
    }

    public void setTrailers(TrailerAdapter trailers) {
        this.trailers.add(trailers);
    }

    public boolean isFavourited() {
        return isFavourited;
    }

    public void setFavourited(boolean favourited) {
        isFavourited = favourited;
    }

    public String getDirector() {
        String[] directorName = null;

        if (this.director != null) {
            directorName = director.split(" ");

        }
        return director != null ? directorName[0] : "N/A";
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {

        return releaseDate != null ? releaseDate : "N/A";
    }

    public float getUserRating() {
        return userRating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackDropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
     *Get the Url of the Image to populate
     */

    public String getPosterURL() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185");
        return builder.build().toString() + posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(popularity);
        parcel.writeString(backdropPath);
        parcel.writeString(title);
        parcel.writeString(voteCount);
        parcel.writeFloat(userRating);
        parcel.writeByte((byte) (isFavourited ? 1 :0));
        parcel.writeInt(runtime);
        parcel.writeString(director);
        parcel.writeList(genres);
        parcel.writeList(trailers);
        parcel.writeList(cast);
        parcel.writeList(reviews);
    }


    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    private MovieItem(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        popularity = in.readString();
        backdropPath = in.readString();
        title = in.readString();
        voteCount = in.readString();
        userRating = in.readFloat();
        runtime = in.readInt();
        director = in.readString();
        isFavourited = in.readByte() != 0;
        in.readList(genres, List.class.getClassLoader());
        in.readList(trailers, List.class.getClassLoader());
        in.readList(cast, List.class.getClassLoader());
        in.readList(reviews, List.class.getClassLoader());


    }


}
