package id.atsiri.mymoviecatalogue.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private int favId;
    private String backdropPath;
    private String posterPath;
    private String title;
    private String voteAverage;
    private String overView;
    private String status;

    public int getFavId() { return favId;  }

    public void setFavId(int favId) { this.favId = favId; }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(favId);
        dest.writeString(backdropPath);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(voteAverage);
        dest.writeString(overView);
        dest.writeString(status);
    }

    public Favorite() {

    }

    private Favorite(Parcel in) {
        favId = in.readInt();
        backdropPath = in.readString();
        posterPath = in.readString();
        title = in.readString();
        voteAverage = in.readString();
        overView = in.readString();
        status = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };


}
