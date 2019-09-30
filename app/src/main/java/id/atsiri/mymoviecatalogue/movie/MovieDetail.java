package id.atsiri.mymoviecatalogue.movie;

import org.json.JSONObject;

public class MovieDetail {
    private String movieId;
    private String backdropPath;
    private String posterPath;
    private String title;
    private String voteAverage;
    private String overView;

    public MovieDetail() { }

    public String getMovieId() { return movieId; }

    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getBackdropPath() { return backdropPath; }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() { return posterPath; }

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

    MovieDetail(JSONObject object) {
        try {
            String movieId = object.getString("id");
            String backdropPath = object.getString("backdrop_path");
            String posterPath = object.getString("poster_path");
            String title = object.getString("title");
            String voteAverage = object.getString("vote_average");
            String overView = object.getString("overview");

            this.movieId = movieId;
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
