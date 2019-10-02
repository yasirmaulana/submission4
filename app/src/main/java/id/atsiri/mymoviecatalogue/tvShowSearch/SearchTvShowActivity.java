package id.atsiri.mymoviecatalogue.tvShowSearch;

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
import android.widget.Toast;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.R;

public class SearchTvShowActivity extends AppCompatActivity {
    private SearchTvShowAdapter adapter;
    private ProgressBar progressBar;
    private SearchTvShowViewModel searchTvShowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv_shows);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tvshow_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search_tvshow)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_tvshow));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(SearchTvShowActivity.this, query, Toast.LENGTH_SHORT).show();

                    searchTvShowViewModel = ViewModelProviders.of(SearchTvShowActivity.this).get(SearchTvShowViewModel.class);
                    searchTvShowViewModel.getTvShows().observe(SearchTvShowActivity.this, getTvShow);

                    adapter = new SearchTvShowAdapter();
                    adapter.notifyDataSetChanged();

                    RecyclerView recyclerView = findViewById(R.id.rv_tvshow_search);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchTvShowActivity.this));
                    recyclerView.setAdapter(adapter);

                    progressBar = findViewById(R.id.progressBar);

                    showLoading(true);
                    searchTvShowViewModel.setTvShowSearch(query);

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

    private Observer<ArrayList<SearchTvShows>> getTvShow = new Observer<ArrayList<SearchTvShows>>() {
        @Override
        public void onChanged(@Nullable ArrayList<SearchTvShows> searchTvShows) {
            if (searchTvShows != null){
                adapter.setData(searchTvShows);
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
