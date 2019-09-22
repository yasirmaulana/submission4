package id.atsiri.mymoviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import id.atsiri.mymoviecatalogue.db.FavoriteHelper;
import id.atsiri.mymoviecatalogue.entity.Favorite;

public class FavoriteDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvVoteAverage;

    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    public static final int REQUEST_UPDATE = 200;

    private Favorite favorite;
    private FavoriteHelper favoriteHelper;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);

        tvTitle = findViewById(R.id.tv_title_fav_detail);
        tvVoteAverage = findViewById(R.id.tv_voteaverage_fav_detail);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
        if (favorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            tvTitle.setText(favorite.getTitle());
            tvVoteAverage.setText(favorite.getVoteAverage());
        } else {
            favorite = new Favorite();
        }



    }
}
