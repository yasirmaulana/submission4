package id.atsiri.mymoviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbcatalogueapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format(
      "CREATE TABLE %s" + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
              " %s TEXT NOT NULL, " +
              " %s TEXT NOT NULL, " +
              " %s TEXT NOT NULL, " +
              " %s TEXT NOT NULL, " +
              " %s TEXT NOT NULL, " +
              " %s TEXT NOT NULL, " +
              " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE,
            DatabaseContract.FavoriteColumns._ID,
            DatabaseContract.FavoriteColumns.FAVID,
            DatabaseContract.FavoriteColumns.BACKDROPPATH,
            DatabaseContract.FavoriteColumns.POSTERPATH,
            DatabaseContract.FavoriteColumns.TITLE,
            DatabaseContract.FavoriteColumns.VOTEAVERAGE,
            DatabaseContract.FavoriteColumns.OVERVIEW,
            DatabaseContract.FavoriteColumns.STATUS
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE);
        onCreate(db);
    }
}
