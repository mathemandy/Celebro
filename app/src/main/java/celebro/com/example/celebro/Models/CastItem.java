package celebro.com.example.celebro.Models;

import android.os.Parcel;
import android.os.Parcelable;

import celebro.com.example.celebro.Utils.HashUtils;

/**
 * Created by 502575517 on 3/9/2016.
 */
public class CastItem implements Parcelable {

    private String actorName;
    private String profile_photo;
    private String character;


    /**
     * Constructor
     *
     */

    public CastItem (String character, String actorName, String profile_photo){
        this.character = character;
        this.actorName = actorName;
        this.profile_photo = profile_photo;
    }

    public String getImportedHashCode()
    {
        StringBuilder builder = new StringBuilder();

        builder.append ("character").append(character == null? "": character)
                .append ("actorName").append(actorName == null? "": actorName)
                .append("profile_photo").append (profile_photo == null? "": profile_photo);
        return HashUtils.computeWeakHash(builder.toString());
}
    /**
     * GETTERS AND SETTERS
     *
     */

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }


    /**
     * PARCELABLE METHODS
     */

    protected CastItem(Parcel in) {
        actorName = in.readString();
        profile_photo = in.readString();
        character = in.readString();
    }

    public static final Creator<CastItem> CREATOR = new Creator<CastItem>() {
        @Override
        public CastItem createFromParcel(Parcel in) {
            return new CastItem(in);
        }

        @Override
        public CastItem[] newArray(int size) {
            return new CastItem[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actorName);
        dest.writeString(profile_photo);
        dest.writeString(character);
    }
}
