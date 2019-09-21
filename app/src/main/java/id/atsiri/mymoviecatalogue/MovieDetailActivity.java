package id.atsiri.mymoviecatalogue;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.atsiri.mymoviecatalogue.db.FavoriteHelper;
import id.atsiri.mymoviecatalogue.entity.Favorite;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    private ProgressBar progressBar;
    private MovieDetailViewModel movieDetailViewModel;

    public Favorite favorite;
    public FavoriteHelper favoriteHelper;

    public static final int RESULT_ADD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getMovieDetail().observe(this, getModelMovieDetail);

        progressBar = findViewById(R.id.progressBar);

        MovieItems movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        int movieId = movie.getId();

        movieDetailViewModel.setMovieDetail(movieId);
        showLoading(true);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favoriteHelper.open();

        Button btnSubmitFav;
        btnSubmitFav = findViewById(R.id.btn_submit_fav);
        btnSubmitFav.setOnClickListener(this);

    }

    private Observer<MovieDetail> getModelMovieDetail = new Observer<MovieDetail>() {
        @Override
        public void onChanged(@Nullable MovieDetail movieDetail) {
            ImageView backdropDetail;
            ImageView posterDetail;
            TextView tvTitleDetail;
            TextView tvUserScoreDetail;
            TextView tvOverview;

            if (movieDetail != null) {
                String sPath = "https://image.tmdb.org/t/p/w342";

                backdropDetail = findViewById(R.id.backdrop_detail);
                posterDetail = findViewById(R.id.poster_detail);
                tvTitleDetail = findViewById(R.id.tv_tittle_detail);
                tvUserScoreDetail = findViewById(R.id.tv_user_score_detail);
                tvOverview = findViewById(R.id.tv_overview);

                Glide.with(MovieDetailActivity.this)
                        .load(sPath + movieDetail.getBackdropPath())
                        .into(backdropDetail);
                Glide.with(MovieDetailActivity.this)
                        .load(sPath + movieDetail.getPosterPath())
                        .into(posterDetail);
                tvTitleDetail.setText(movieDetail.getTitle());
                tvUserScoreDetail.setText(movieDetail.getVoteAverage());
                tvOverview.setText(movieDetail.getOverView());

                showLoading(false);
            }
        }
    };

    private Observer<MovieDetail> getMovieDetailModel = new Observer<MovieDetail>() {
        @Override
        public void onChanged(@Nullable MovieDetail movieDetail) {
            favorite = new Favorite();
            String idMovie = movieDetail.getMovieId();

            favorite.setFavId(movieDetail.getMovieId());
            favorite.setBackdropPath(movieDetail.getBackdropPath());
            favorite.setPosterPath(movieDetail.getPosterPath());
            favorite.setTitle(movieDetail.getTitle());
            favorite.setVoteAverage(movieDetail.getVoteAverage());
            favorite.setOverView(movieDetail.getOverView());
            favorite.setStatus("movie");

            long result = favoriteHelper.insertFavorite(favorite);
            if (result > 0){
                Toast.makeText(MovieDetailActivity.this, "set Favorite, success " + idMovie, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MovieDetailActivity.this, "set Favorite, fail", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public MovieDetailActivity() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_fav) {
            MovieItems movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            int movieId = movie.getId();

//            movieDetailViewModel.setMovieDetail(movieId);

            movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
            movieDetailViewModel.getMovieDetail().observe(this, getMovieDetailModel);

//            String favid = "fav001";
//            String backdroppath = "http://dimanaitu.com";
//            String posterpath = "http://dimanaitu.com";
//            String title = "beranak dalam kubur";
//            String voteaverage = "6.6";
//            String overview = "wkwkwk, lucu judulnya";
//            String status = "movie";

//            Toast.makeText(this, "ini kode movie " + movieId, Toast.LENGTH_SHORT).show();
//
//            favorite.setFavId("fav001");
//            favorite.setBackdropPath("http://dimanaitu.com");
//            favorite.setPosterPath("http://dimanaitu.com");
//            favorite.setTitle("beranak dalam kubur");
//            favorite.setVoteAverage("6.6");
//            favorite.setOverView("wkwkwk, lucu judulnya");
//            favorite.setStatus("movie");
//
//            long result = favoriteHelper.insertFavorite(favorite);
//            if (result > 0) {
//                favorite.setId((int) result);
//                //            setResult(RESULT_ADD);
//                finish();
//            } else {
//                Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
//            }
        }


    }
}