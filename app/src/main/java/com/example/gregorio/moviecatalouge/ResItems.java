package com.example.gregorio.moviecatalouge;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.example.gregorio.moviecatalouge.DatabaseContract.getColumnInt;
import static com.example.gregorio.moviecatalouge.DatabaseContract.getColumnString;

public class ResItems {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getJudulFilm() {
        return judulFilm;
    }


    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    private int id;
    private String judulFilm;
    private String deskripsi;
    private String Tanggal;
    private String vote;

    public String getVote() {
        return vote;
    }


    public String getPopular() {
        return popular;
    }


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    private String popular;
    private String subtitle;

    public String getPoster() {
        return Poster;
    }


    private String Poster;


    public ResItems(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.judulFilm = getColumnString(cursor, DatabaseContract.FavouriteColumns.TITLE);
        this.deskripsi = getColumnString(cursor, DatabaseContract.FavouriteColumns.DESCRIPTION);
        this.Tanggal = getColumnString(cursor, DatabaseContract.FavouriteColumns.DATE);
        this.Poster = getColumnString(cursor, DatabaseContract.FavouriteColumns.POSTER);
        this.popular = getColumnString(cursor, DatabaseContract.FavouriteColumns.POPULARITY);
        this.vote = getColumnString(cursor, DatabaseContract.FavouriteColumns.VOTING);
        this.subtitle = getColumnString(cursor, DatabaseContract.FavouriteColumns.SUBTITLE);
    }

    @Override
    public String toString(){
        return
                "ResItems{" +
                        "id = '" + id + '\'' +
                        ",judulFilm = '" + judulFilm + '\'' +
                        ",deskripsi = '" + deskripsi + '\'' +
                        ",Tanggal = '" + Tanggal + '\'' +
                        ",Poster = '" + Poster + '\'' +
                        ",popular = '" + popular + '\'' +
                        ",vote = '" + vote + '\'' +
                        ",subtitle = '" + subtitle + '\'' +
                        "}";
    }

}
