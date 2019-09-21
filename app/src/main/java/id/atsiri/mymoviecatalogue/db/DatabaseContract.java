package id.atsiri.mymoviecatalogue.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVORITE = "favorite2";

    static final class FavoriteColumns implements BaseColumns {

        static String FAVID = "favid";
        static String BACKDROPPATH = "backdroppath";
        static String POSTERPATH = "posterpath";
        static String TITLE = "title";
        static String VOTEAVERAGE = "voteaverage";
        static String OVERVIEW = "overview";
        static String STATUS = "status";

    }

}
