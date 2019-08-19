package id.atsiri.mymoviecatalogue;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    private ProgressBar progressBar;
    private MovieDetailViewModel movieDetailViewModel;

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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}