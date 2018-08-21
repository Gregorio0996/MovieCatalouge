package com.example.gregorio.favouritelist;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract  {
    public static final String AUTHORITY = "com.example.gregorio.moviecatalouge";
    public static String TABLE_NAME = "movie_favourite";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final class FavouriteColumns implements BaseColumns{
        public static String TITLE = "judul";
        public static String DATE = "date";
        public static String DESCRIPTION = "description";
        public static String SUBTITLE = "subtitle";
        public static String POPULARITY = "popularity";
        public static String VOTING = "voting";
        public static String POSTER = "poster";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble (Cursor cursor, String columnName){
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

}
