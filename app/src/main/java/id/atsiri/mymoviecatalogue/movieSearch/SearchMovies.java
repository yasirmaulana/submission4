package id.atsiri.mymoviecatalogue.movieSearch;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import id.atsiri.mymoviecatalogue.BuildConfig;

public class SearchMovies implements Parcelable {
    private int id;
    private String backdropPath, voteAvarage, title, releaseDate;

    protected SearchMovies(Parcel in) {
        id = in.readInt();
        backdropPath = in.readString();
        voteAvarage = in.readString();
        title = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<SearchMovies> CREATOR = new Creator<SearchMovies>() {
        @Override
        public SearchMovies createFromParcel(Parcel in) { return new SearchMovies(in); }

        @Override
        public SearchMovies[] newArray(int size) { return new SearchMovies[size]; }
    };

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getBackdropPath() {
        String sbackdropPath = BuildConfig.TMDB_PATH_PICTURE + backdropPath;
        return sbackdropPath;
    }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public String getVoteAvarage() { return voteAvarage; }

    public void setVoteAvarage(String voteAvarage) { this.voteAvarage = voteAvarage; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    SearchMovies(JSONObject object) {
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
        dest.writeInt(id);
        dest.writeString(backdropPath);
        dest.writeString(voteAvarage);
        dest.writeString(title);
        dest.writeString(releaseDate);
    }
}
