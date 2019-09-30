package id.atsiri.mymoviecatalogue.movieSearch;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.R;

public class SearchMovieActivity extends AppCompatActivity {
    private SearchMovieAdapter adapter;
    private ProgressBar progressBar;
    private SearchMovieViewModel searchMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    searchMovieViewModel = ViewModelProviders.of(SearchMovieActivity.this).get(SearchMovieViewModel.class);
                    searchMovieViewModel.getMovies().observe(SearchMovieActivity.this, getMovie);

                    adapter = new SearchMovieAdapter();
                    adapter.notifyDataSetChanged();

                    RecyclerView recyclerView = findViewById(R.id.rv_movie_search);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchMovieActivity.this));
                    recyclerView.setAdapter(adapter);

                    progressBar = findViewById(R.id.progressBar);

                    showLoading(true);
                    searchMovieViewModel.setMovieSearch(query);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    private Observer<ArrayList<SearchMovies>> getMovie = new Observer<ArrayList<SearchMovies>>() {
        @Override
        public void onChanged(@Nullable ArrayList<SearchMovies> searchMovies) {
            if (searchMovies != null) {
                adapter.setData(searchMovies);
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
