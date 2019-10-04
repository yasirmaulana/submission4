package id.atsiri.mymoviecatalogue;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import id.atsiri.mymoviecatalogue.db.DatabaseContract;
import id.atsiri.mymoviecatalogue.db.DatabaseHelper;
import id.atsiri.mymoviecatalogue.db.FavoriteHelper;

public class FavoriteProvider extends ContentProvider {
    private static final String AUTHORITY = "id.atsiri.mymoviecatalogue";
    private static final String PATH = "favorite";
    private static final String SCHEMA = "content://" + AUTHORITY + "/" + PATH;

    public static final Uri CONTENT_URI = Uri.parse(SCHEMA);

    private static final int FAVORITE = 1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, PATH, FAVORITE);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case FAVORITE:
                cursor =  database.query(FavoriteHelper.DATABASE_TABLE, DatabaseHelper.POSTER_COLUMN, null, null, null, null, null, null);
                break;
            default:
                throw new IllegalArgumentException("What this is URI");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
