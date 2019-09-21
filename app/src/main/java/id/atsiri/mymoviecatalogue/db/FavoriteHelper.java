package id.atsiri.mymoviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.entity.Favorite;

import static android.provider.BaseColumns._ID;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.BACKDROPPATH;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.FAVID;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.POSTERPATH;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.STATUS;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.TITLE;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.FavoriteColumns.VOTEAVERAGE;
import static id.atsiri.mymoviecatalogue.db.DatabaseContract.TABLE_FAVORITE;


public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Favorite> getAllFavorites() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setFavId(cursor.getString(cursor.getColumnIndexOrThrow(FAVID)));
                favorite.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROPPATH)));
                favorite.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(VOTEAVERAGE)));
                favorite.setOverView(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int getFav(long id) {
        String countQuery = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + FAVID + "=" + id;

        Cursor cursor = database.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public long insertFavorite(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(FAVID, favorite.getFavId());
        args.put(BACKDROPPATH, favorite.getBackdropPath());
        args.put(POSTERPATH, favorite.getPosterPath());
        args.put(TITLE, favorite.getTitle());
        args.put(VOTEAVERAGE, favorite.getVoteAverage());
        args.put(OVERVIEW, favorite.getOverView());
        args.put(STATUS, favorite.getStatus());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateFavorite(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(FAVID, favorite.getFavId());
        args.put(BACKDROPPATH, favorite.getBackdropPath());
        args.put(POSTERPATH, favorite.getPosterPath());
        args.put(TITLE, favorite.getTitle());
        args.put(VOTEAVERAGE, favorite.getVoteAverage());
        args.put(OVERVIEW, favorite.getOverView());
        args.put(STATUS, favorite.getStatus());

        return database.update(DATABASE_TABLE, args, FAVID + "= '" + favorite.getFavId() + "'", null);
    }

    public int deleteFavorite(int id) {
        return database.delete(TABLE_FAVORITE, _ID + " = '" + id + "'", null);
    }


}
