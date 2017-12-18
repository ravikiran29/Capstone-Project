package data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by ravikiranpathade on 12/15/17.
 */

public class NewsContract {

    public static final String AUTHORITY = "com.example.ravikiranpathade.newstrends";
    public static final Uri baseUri = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";

    public static final class NewsFavoritesEntry implements BaseColumns{

        public static final Uri FINAL_URI = baseUri.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_ID = "ID"; //TODO Check if needed
        public static final String COLUMN_NAME_TITLE = "TITLE";
        public static final String COLUMN_NAME_AUTHOR = "AUTHOR";
        public static final String COLUMN_NAME_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_NAME_URL = "URL";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "URLTOIMAGE";
        public static final String COLUMN_NAME_PUBLISHED_AT = "PUBLISHEDAT";
        public static final String COLUMN_NAME_SOURCE_ID = "SOURCEID";
        public static final String COLUMN_NAME_SOURCE_NAME ="SOURCENAME";
        public static final String COLUMN_NAME_DATE = "DATE";

    }
}