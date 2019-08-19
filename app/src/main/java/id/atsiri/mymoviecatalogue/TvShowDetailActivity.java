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

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    ImageView backdropDetail;
    ImageView posterDetail;
    TextView tvTitleDetail;
    TextView tvUserScoreDetail;
    TextView tvOverview;

    private ProgressBar progressBar;
    private TvShowDetailViewModel tvShowDetailViewModel;

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
    }

    private Observer<TvShowDetail> getModelTvShowDetail = new Observer<TvShowDetail>() {
        @Override
        public void onChanged(@Nullable TvShowDetail tvShowDetail) {
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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
