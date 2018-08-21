package com.example.gregorio.moviecatalouge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MOVIE = "create table " + DatabaseContract.TABLE_NAME + " (" +
                DatabaseContract.FavouriteColumns._ID + " integer primary key autoincrement, " +
                DatabaseContract.FavouriteColumns.POSTER + " text not null, "+
                DatabaseContract.FavouriteColumns.TITLE + " text not null, " +
                DatabaseContract.FavouriteColumns.DATE + " text not null, " +
                DatabaseContract.FavouriteColumns.DESCRIPTION + " text not null, "+
                DatabaseContract.FavouriteColumns.SUBTITLE + " text not null, "+
                DatabaseContract.FavouriteColumns.POPULARITY + " text not null, "+
                DatabaseContract.FavouriteColumns.VOTING + " text not null"+ " );";
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
    onCreate(sqLiteDatabase);
    }
}
