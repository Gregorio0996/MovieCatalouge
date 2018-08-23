package com.example.gregorio.favouritelist;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.example.gregorio.favouritelist.DatabaseContract.getColumnInt;
import static com.example.gregorio.favouritelist.DatabaseContract.getColumnString;

public class FilmItemss implements Parcelable {
    public static final Creator<FilmItemss> CREATOR = new Creator<FilmItemss>() {
        @Override
        public FilmItemss createFromParcel(Parcel in) {
            return new FilmItemss(in);
        }

        @Override
        public FilmItemss[] newArray(int size) {
            return new FilmItemss[size];
        }
    };

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


    public FilmItemss(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.judulFilm = getColumnString(cursor, DatabaseContract.FavouriteColumns.TITLE);
        this.deskripsi = getColumnString(cursor, DatabaseContract.FavouriteColumns.DESCRIPTION);
        this.Tanggal = getColumnString(cursor, DatabaseContract.FavouriteColumns.DATE);
        this.Poster = getColumnString(cursor, DatabaseContract.FavouriteColumns.POSTER);
        this.popular = getColumnString(cursor, DatabaseContract.FavouriteColumns.POPULARITY);
        this.vote = getColumnString(cursor, DatabaseContract.FavouriteColumns.VOTING);
        this.subtitle = getColumnString(cursor, DatabaseContract.FavouriteColumns.SUBTITLE);
    }

    public FilmItemss(Parcel in) {
        this.id = in.readInt();
        this.judulFilm = in.readString();
        this.Tanggal = in.readString();
        this.Poster = in.readString();
        this.popular = in.readString();
        this.vote = in.readString();
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


}