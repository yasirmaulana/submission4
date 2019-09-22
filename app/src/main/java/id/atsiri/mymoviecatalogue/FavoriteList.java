package id.atsiri.mymoviecatalogue;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.db.FavoriteHelper;
import id.atsiri.mymoviecatalogue.entity.Favorite;

public class FavoriteList extends AppCompatActivity implements View.OnClickListener, LoadFavoritesCallback {
    private RecyclerView rvFavorite;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavoriteAdapter adapter;
    private FavoriteHelper favoriteHelper;
    private String favid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Favorites");

        rvFavorite = findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        favoriteHelper.open();

        progressBar = findViewById(R.id.progressbar_favorite_rv);
//        fabAdd = findViewById(R.id.fab_add);
//        fabAdd.setOnClickListener(this);

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
//        if (view.getId() == R.id.fab_add) {
//            Intent intent = new Intent(FavoriteList.this, FavoritAddUpdateActivity.class);
//            startActivityForResult(intent, FavoritAddUpdateActivity.REQUEST_ADD);
//        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == FavoritAddUpdateActivity.REQUEST_ADD) {
                if (resultCode == FavoritAddUpdateActivity.RESULT_ADD) {
                    Favorite favorite = data.getParcelableExtra(FavoritAddUpdateActivity.EXTRA_FAVORITE);
                    adapter.addItem(favorite);
                    rvFavorite.smoothScrollToPosition(adapter.getItemCount() - 1);
                    showSnackbarMessage("Satu item berhasil ditambahkan");
                }
            }
            else if (requestCode == FavoritAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == FavoritAddUpdateActivity.RESULT_UPDATE) {
                    Favorite favorite = data.getParcelableExtra(FavoritAddUpdateActivity.EXTRA_FAVORITE);
                    int position = data.getIntExtra(FavoritAddUpdateActivity.EXTRA_POSITION, 0);
                    adapter.updateItem(position, favorite);
                    rvFavorite.smoothScrollToPosition(position);
                    showSnackbarMessage("Satu item berhasil diubah");
                }
                else if (resultCode == FavoritAddUpdateActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(FavoritAddUpdateActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                    showSnackbarMessage("Satu item berhasil dihapus");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }
}
