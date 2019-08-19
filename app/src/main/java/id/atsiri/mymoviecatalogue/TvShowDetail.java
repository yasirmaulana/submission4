package id.atsiri.mymoviecatalogue;

import org.json.JSONObject;

public class TvShowDetail {
    private String backdropPath;
    private String posterPath;
    private String title;
    private String voteAverage;
    private String overView;

    public TvShowDetail() { }

    public String getBackdropPath() { return backdropPath; }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getVoteAverage() { return voteAverage; }

    public void setVoteAverage(String voteAverage) { this.voteAverage = voteAverage; }

    public String getOverView() { return overView; }

    public void setOverView(String overView) { this.overView = overView; }

    TvShowDetail(JSONObject object) {
        try {
            String backdropPath = object.getString("backdrop_path");
            String posterPath = object.getString("poster_path");
            String title = object.getString("name");
            String voteAverage = object.getString("vote_average");
            String overView = object.getString("overview");

            this.backdropPath = backdropPath;
            this.posterPath = posterPath;
            this.title = title;
            this.voteAverage = voteAverage;
            this.overView = overView;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
