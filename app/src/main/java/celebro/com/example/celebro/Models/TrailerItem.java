package celebro.com.example.celebro.Models;

import android.os.Parcel;
import android.os.Parcelable;

import celebro.com.example.celebro.Utils.HashUtils;

/**
 * Created by 502575517 on 3/9/2016.
 */
public class TrailerItem implements Parcelable{

    private String name;

    private String source;

    public TrailerItem (){

        this.name = "unavailable";
        this.source = "unavailble";

    }

    public TrailerItem(String nName, String nSource){
        this.name = nName;
        this.source = nSource;

    }

    protected TrailerItem(Parcel in) {
        name = in.readString();
        source = in.readString();
    }

    public static final Creator<TrailerItem> CREATOR = new Creator<TrailerItem>() {
        @Override
        public TrailerItem createFromParcel(Parcel in) {
            return new TrailerItem(in);
        }

        @Override
        public TrailerItem[] newArray(int size) {
            return new TrailerItem[size];
        }
    };
    public String getImportedHashCode()
    {
        StringBuilder builder= new StringBuilder();

        builder.append("name").append(name == null  ? "" : name)
                .append("source").append(source == null ? "" : source);

        return HashUtils.computeWeakHash(builder.toString());
    }

    /**
     * GETTERS AND SETTERS
     *
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(source);
    }
}
