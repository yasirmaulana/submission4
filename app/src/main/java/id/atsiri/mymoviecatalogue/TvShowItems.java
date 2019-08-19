package id.atsiri.mymoviecatalogue;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

//public class TvShowItems implements Parcelable {
public class TvShowItems implements Parcelable {

    private int id;
    private String backdropPath, voteAverage, title, firstAirDate;

    TvShowItems() {
    }

    protected TvShowItems(Parcel in) {
        id = in.readInt();
        backdropPath = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        firstAirDate = in.readString();
    }

    public static final Creator<TvShowItems> CREATOR = new Creator<TvShowItems>() {
        @Override
        public TvShowItems createFromParcel(Parcel in) {
            return new TvShowItems(in);
        }

        @Override
        public TvShowItems[] newArray(int size) {
            return new TvShowItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        String sbackdropPath = "https://image.tmdb.org/t/p/w342" + backdropPath;
        return sbackdropPath;
//        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    TvShowItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String backdropPath = object.getString("backdrop_path");
            String voteAverage = object.getString("vote_average");
            String title = object.getString("name");
            String firstAirDate = object.getString("first_air_date");

            this.id = id;
            this.backdropPath = backdropPath;
            this.voteAverage = voteAverage;
            this.title = title;
            this.firstAirDate = firstAirDate;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(backdropPath);
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(firstAirDate);
    }
}
