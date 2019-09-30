package id.atsiri.mymoviecatalogue.favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.LoadFavoritesCallback;
import id.atsiri.mymoviecatalogue.MainActivity;
import id.atsiri.mymoviecatalogue.R;
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
        if (favorites.size() > 0) {
            adapter.setListFavorites(favorites);
        } else {
            Toast.makeText(this, "Tidak ada data di Favorit" , Toast.LENGTH_SHORT).show();
        }

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

    @Override
    public void onBackPressed() {
        Intent moveToHome = new Intent(FavoriteList.this, MainActivity.class);
        startActivity(moveToHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent moveToHome = new Intent(FavoriteList.this, MainActivity.class);
                startActivity(moveToHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
