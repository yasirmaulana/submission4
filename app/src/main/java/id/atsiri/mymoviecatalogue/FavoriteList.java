package id.atsiri.mymoviecatalogue;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.adapter.FavoriteAdapter;
import id.atsiri.mymoviecatalogue.db.FavoriteHelper;
import id.atsiri.mymoviecatalogue.entity.Favorite;

public class FavoriteList extends AppCompatActivity implements View.OnClickListener, LoadFavoritesCallback {
    private RecyclerView rvFavorite;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavoriteAdapter adapter;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        rvFavorite = findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favoriteHelper.open();

        progressBar = findViewById(R.id.progressbar_favorite_rv);

        adapter = new FavoriteAdapter(this);
        rvFavorite.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadFavoritesAsync(favoriteHelper, this).execute();
        } else {
            ArrayList<Favorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListFavorites(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFavorites());
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Favorite> favorites) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListFavorites(favorites);
    }

    private static class LoadFavoritesAsync extends AsyncTask<Void, Void, ArrayList<Favorite>> {

        private final WeakReference<FavoriteHelper> weakFavoriteHelper;
        private final WeakReference<LoadFavoritesCallback> weakCallBack;

        private LoadFavoritesAsync(FavoriteHelper favoriteHelper, LoadFavoritesCallback callback) {
            weakFavoriteHelper = new WeakReference<>(favoriteHelper);
            weakCallBack = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallBack.get().preExecute();
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            return weakFavoriteHelper.get().getAllFavorites();
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);

            weakCallBack.get().postExecute(favorites);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

}
