package id.atsiri.mymoviecatalogue;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.entity.Favorite;

public interface LoadFavoritesCallback {
    void preExecute();
    void postExecute(ArrayList<Favorite> favorites);

}
