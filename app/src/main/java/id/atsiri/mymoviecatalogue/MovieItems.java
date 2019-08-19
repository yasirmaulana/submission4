package id.atsiri.mymoviecatalogue;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MovieItems implements Parcelable {

    private int id;
    private String backdropPath, voteAvarage, title, releaseDate;

    protected MovieItems(Parcel in) {
        id = in.readInt();
        backdropPath = in.readString();
        voteAvarage = in.readString();
        title = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };

    public String getBackdropPath() {
        String sbackdropPath = "https://image.tmdb.org/t/p/w342" + backdropPath;
        return sbackdropPath;
    }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteAvarage() {
        return voteAvarage;
    }

    public void setVoteAvarage(String voteAvarage) {
        this.voteAvarage = voteAvarage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String backdropPath = object.getString("backdrop_path");
            String voteAvarage = object.getString("vote_average");
            String title = object.getString("title");
            String releaseDate = object.getString("release_date");

            this.id = id;
            this.backdropPath = backdropPath;
            this.voteAvarage = voteAvarage;
            this.title = title;
            this.releaseDate = releaseDate;
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
        dest.writeInt(this.id);
    }
}