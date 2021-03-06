package id.atsiri.mymoviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import id.atsiri.mymoviecatalogue.favorite.FavoriteList;
import id.atsiri.mymoviecatalogue.movie.MovieListFragment;
import id.atsiri.mymoviecatalogue.movieSearch.SearchMovieActivity;
import id.atsiri.mymoviecatalogue.tvShowSearch.SearchTvShowActivity;
import id.atsiri.mymoviecatalogue.tvshow.TvShowListFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_movies:
                    fragment = new MovieListFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigation_tvshow:
                    fragment = new TvShowListFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_movies);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_setting) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        if (item.getItemId() == R.id.action_favorite) {
            Intent favoriteList = new Intent(MainActivity.this, FavoriteList.class);
            startActivity(favoriteList);
        }

        if (item.getItemId() == R.id.action_movie_search) {
            Intent searchMovie = new Intent(MainActivity.this, SearchMovieActivity.class);
            startActivity(searchMovie);
        }

        if (item.getItemId() == R.id.action_tvshow_search) {
            Intent searchMovie = new Intent(MainActivity.this, SearchTvShowActivity.class);
            startActivity(searchMovie);
        }
        return super.onOptionsItemSelected(item);
    }
}
