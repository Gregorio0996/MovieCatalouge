package com.example.gregorio.moviecatalouge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class FavouriteHelper {
    private static String TABLE_NAME = DatabaseContract.TABLE_NAME;

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public FavouriteHelper(Context context) {
        this.context = context;
    }

    public FavouriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<FilmModel> getAllData(){
        ArrayList<FilmModel> arrayList = new ArrayList<FilmModel>();
        Cursor cursor = database.query(TABLE_NAME
                ,null
                ,null
                ,null
                ,null
                ,null, _ID + " DESC"
                ,null
        );
        cursor.moveToFirst();
        FilmModel filmModel;
        if (cursor.getCount()>0){
            do {
                filmModel = new FilmModel();
                filmModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID)));
                filmModel.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER)));
                filmModel.setJudulFilm(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE)));
                filmModel.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.DATE)));
                filmModel.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.DESCRIPTION)));
                filmModel.setPopular(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POPULARITY)));
                filmModel.setVote(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.VOTING)));
                filmModel.setSubtitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.SUBTITLE)));

            }while (! cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(FilmModel filmModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.FavouriteColumns.TITLE, filmModel.getJudulFilm());
        contentValues.put(DatabaseContract.FavouriteColumns.DESCRIPTION, filmModel.getDeskripsi());
        contentValues.put(DatabaseContract.FavouriteColumns.DATE, filmModel.getTanggal());
        contentValues.put(DatabaseContract.FavouriteColumns.POSTER, filmModel.getPoster());
        contentValues.put(DatabaseContract.FavouriteColumns.POPULARITY, filmModel.getPopular());
        contentValues.put(DatabaseContract.FavouriteColumns.SUBTITLE, filmModel.getSubtitle());
        contentValues.put(DatabaseContract.FavouriteColumns.VOTING, filmModel.getVote());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int delete (int id){
        return database.delete(DatabaseContract.TABLE_NAME, DatabaseContract.FavouriteColumns._ID+ " = '"+id+"'", null);
    }

    public Cursor queryByIDProvider(String id) {
        return database.query(TABLE_NAME, null
                , DatabaseContract.FavouriteColumns.TITLE + " = ? "
                , new String[]{id}
                , null
                , null
                , null
                , null
        );
    }

    public Cursor queryProvider() {
        return database.query(TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC"
        );
    }

    public long insertProvider(ContentValues values){
        return database.insert(TABLE_NAME, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return  database.update(TABLE_NAME, values, _ID + " = ? ", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(TABLE_NAME, _ID + " = ? ",new String[]{id});
    }


}
