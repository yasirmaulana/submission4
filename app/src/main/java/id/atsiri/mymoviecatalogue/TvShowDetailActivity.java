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

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TVSHOW = "extra_tvshow";

    private ProgressBar progressBar;
    private TvShowDetailViewModel tvShowDetailViewModel;

    public Favorite favorite;
    public FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        tvShowDetailViewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
        tvShowDetailViewModel.getTvShowDetail().observe(this, getModelTvShowDetail);

        progressBar = findViewById(R.id.progressBar);

        TvShowItems tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        int tvShowId = tvshow.getId();

        tvShowDetailViewModel.setTvShowDetail(tvShowId);
        showLoading(true);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favoriteHelper.open();

        Button btnSubmitFavTv;
        btnSubmitFavTv = findViewById(R.id.btn_submit_fav_tv);
        btnSubmitFavTv.setOnClickListener(this);

    }

    private Observer<TvShowDetail> getModelTvShowDetail = new Observer<TvShowDetail>() {
        @Override
        public void onChanged(@Nullable TvShowDetail tvShowDetail) {
            ImageView backdropDetail;
            ImageView posterDetail;
            TextView tvTitleDetail;
            TextView tvUserScoreDetail;
            TextView tvOverview;

            if (tvShowDetail != null) {
                String sPath = "https://image.tmdb.org/t/p/w342";

                backdropDetail = findViewById(R.id.backdrop_detail);
                posterDetail = findViewById(R.id.poster_detail);
                tvTitleDetail = findViewById(R.id.title_detail);
                tvUserScoreDetail = findViewById(R.id.vote_average_detail);
                tvOverview = findViewById(R.id.overview_detail);

                Glide.with(TvShowDetailActivity.this)
                        .load(sPath + tvShowDetail.getBackdropPath())
                        .into(backdropDetail);
                Glide.with(TvShowDetailActivity.this)
                        .load(sPath + tvShowDetail.getPosterPath())
                        .into(posterDetail);
                tvTitleDetail.setText(tvShowDetail.getTitle());
                tvUserScoreDetail.setText(tvShowDetail.getVoteAverage());
                tvOverview.setText(tvShowDetail.getOverView());

                showLoading(false);
            }
        }
    };

    private Observer<TvShowDetail> getTvShowDetailModel = new Observer<TvShowDetail>() {
        @Override
        public void onChanged(@Nullable TvShowDetail tvShowDetail) {
            favorite = new Favorite();
            favorite.setFavId(tvShowDetail.getTvShowId());
            favorite.setBackdropPath(tvShowDetail.getBackdropPath());
            favorite.setPosterPath(tvShowDetail.getPosterPath());
            favorite.setTitle(tvShowDetail.getTitle());
            favorite.setVoteAverage(tvShowDetail.getVoteAverage());
            favorite.setOverView(tvShowDetail.getOverView());
            favorite.setStatus("Tv Show");

            long result = favoriteHelper.insertFavorite(favorite);
            if (result > 0){
                Toast.makeText(TvShowDetailActivity.this, "set Favorite, success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TvShowDetailActivity.this, "set Favorite, fail", Toast.LENGTH_SHORT).show();
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

    public TvShowDetailActivity() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_fav_tv) {
            TvShowItems tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            int tvShowId = tvshow.getId();

            int result = favoriteHelper.getFav(tvShowId);
            if (result > 0) {
                Toast.makeText(this, "Tv Show ini sudah ada di Favorite ", Toast.LENGTH_SHORT).show();
            } else {
                tvShowDetailViewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
                tvShowDetailViewModel.getTvShowDetail().observe(this, getTvShowDetailModel);
            }

        }
    }
}
