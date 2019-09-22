package id.atsiri.mymoviecatalogue;

import android.content.Intent;
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

public class FavoriteDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_FAVORITE = "extra_favorite";

    private Favorite favorite;
    private FavoriteHelper favoriteHelper;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView backdropDetail, posterDetail;
        TextView tvTitle, tvVoteAverage, tvOverview;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);

        backdropDetail = findViewById(R.id.backdrop_fav_detail);
        posterDetail = findViewById(R.id.poster_fav_detail);
        tvTitle = findViewById(R.id.tv_title_fav_detail);
        tvVoteAverage = findViewById(R.id.tv_voteaverage_fav_detail);
        tvOverview = findViewById(R.id.tv_overview_fav_detail);
        progressBar = findViewById(R.id.progressBar_fav_detail);

        showLoading(true);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favoriteHelper.open();

        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);

        if (favorite != null) {
            String sPath = "https://image.tmdb.org/t/p/w342";
            Glide.with(FavoriteDetailActivity.this)
                    .load(sPath + favorite.getBackdropPath())
                    .into(backdropDetail);
            Glide.with(FavoriteDetailActivity.this)
                    .load(sPath + favorite.getPosterPath())
                    .into(posterDetail);
            tvTitle.setText(favorite.getTitle());
            tvVoteAverage.setText(favorite.getVoteAverage());
            tvOverview.setText(favorite.getOverView());

            showLoading(false);
        } else {
            favorite = new Favorite();
        }

        Button btnDelete;
        btnDelete = findViewById(R.id.btn_submit_fav);
        btnDelete.setOnClickListener(this);

    }

    public FavoriteDetailActivity() {

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);

        String favid = favorite.getFavId();
        int del = favoriteHelper.deleteFav(favid);
        if (del > 0) {
            Toast.makeText(this, "Delete berhasil", Toast.LENGTH_SHORT).show();
            Intent moveIntent = new Intent(FavoriteDetailActivity.this, FavoriteList.class);
            startActivity(moveIntent);
        }

    }
}
