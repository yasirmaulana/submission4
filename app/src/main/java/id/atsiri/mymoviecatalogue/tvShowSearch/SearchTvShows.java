package id.atsiri.mymoviecatalogue.tvShowSearch;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import id.atsiri.mymoviecatalogue.BuildConfig;

public class SearchTvShows implements Parcelable {
    private int id;
    private String backdropPath, voteAverage, title, firstAirDate;

    protected SearchTvShows(Parcel in) {
        id = in.readInt();
        backdropPath = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        firstAirDate = in.readString();
    }

    public static final Creator<SearchTvShows> CREATOR = new Creator<SearchTvShows>() {
        @Override
        public SearchTvShows createFromParcel(Parcel in) { return new SearchTvShows(in); }

        @Override
        public SearchTvShows[] newArray(int size) { return new SearchTvShows[size]; }
    };

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getBackdropPath() {
        String sbackdropPath = BuildConfig.TMDB_PATH_PICTURE + backdropPath;
        return sbackdropPath;
    }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public String getVoteAverage() { return voteAverage; }

    public void setVoteAverage(String voteAverage) { this.voteAverage = voteAverage; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getFirstAirDate() { return firstAirDate; }

    public void setFirstAirDate(String firstAirDate) { this.firstAirDate = firstAirDate; }

    public SearchTvShows(JSONObject object) {
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
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(backdropPath);
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(firstAirDate);
    }
}
