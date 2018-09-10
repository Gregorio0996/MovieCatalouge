package com.example.gregorio.moviecatalouge;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

import static android.provider.BaseColumns._ID;
import static com.example.gregorio.moviecatalouge.DatabaseContract.getColumnInt;
import static com.example.gregorio.moviecatalouge.DatabaseContract.getColumnString;

public class FilmItems implements Serializable, Parcelable {


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

    public void setJudulFilm(String judulFilm) {
        this.judulFilm = judulFilm;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

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

    public FilmItems(JSONObject object) {

        try {

            String judulFilm = object.getString("title");
            String deskripsi = object.getString("overview");
            String Tanggal = object.getString("release_date");
            String Poster = object.getString("poster_path");
            String popular = object.getString("popularity");
            String vote = object.getString("vote_average");
            String subtitle = object.getString("original_language");


            this.judulFilm = judulFilm;
            this.deskripsi = deskripsi;
            this.Tanggal = Tanggal;
            this.Poster = Poster;
            this.popular = popular;
            this.vote = vote;
            this.subtitle = subtitle;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilmItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.judulFilm = getColumnString(cursor, DatabaseContract.FavouriteColumns.TITLE);
        this.deskripsi = getColumnString(cursor, DatabaseContract.FavouriteColumns.DESCRIPTION);
        this.Tanggal = getColumnString(cursor, DatabaseContract.FavouriteColumns.DATE);
        this.Poster = getColumnString(cursor, DatabaseContract.FavouriteColumns.POSTER);
        this.popular = getColumnString(cursor, DatabaseContract.FavouriteColumns.POPULARITY);
        this.vote = getColumnString(cursor, DatabaseContract.FavouriteColumns.VOTING);
        this.subtitle = getColumnString(cursor, DatabaseContract.FavouriteColumns.SUBTITLE);
    }

    public FilmItems(Parcel in){
        this.id  =in.readInt();
        this.judulFilm = in.readString();
        this.Tanggal = in.readString();
        this.Poster = in.readString();
        this.popular = in.readString();
        this.vote  = in.readString();
        this.subtitle = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.judulFilm);
        parcel.writeString(this.Tanggal);
        parcel.writeString(this.Poster);
        parcel.writeString(this.popular);
        parcel.writeString(this.vote);
        parcel.writeString(this.subtitle);
    }

    public static final Parcelable.Creator<FilmItems> CREATOR = new Parcelable.Creator<FilmItems>() {
        @Override
        public FilmItems createFromParcel(Parcel parcel) {
            return new FilmItems(parcel);
        }

        @Override
        public FilmItems[] newArray(int size) {
            return new FilmItems[size];
        }
    };
}
