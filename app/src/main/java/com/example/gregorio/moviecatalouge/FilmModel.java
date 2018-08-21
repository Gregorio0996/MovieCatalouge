package com.example.gregorio.moviecatalouge;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class FilmModel implements Parcelable {
    int id;

    protected FilmModel(Parcel in) {
        id = in.readInt();
        judulFilm = in.readString();
        deskripsi = in.readString();
        Tanggal = in.readString();
        vote = in.readString();
        popular = in.readString();
        subtitle = in.readString();
        Poster = in.readString();
    }

    public static final Creator<FilmModel> CREATOR = new Creator<FilmModel>() {
        @Override
        public FilmModel createFromParcel(Parcel in) {
            return new FilmModel(in);
        }

        @Override
        public FilmModel[] newArray(int size) {
            return new FilmModel[size];
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

    public void setJudulFilm(String judulFilm) {
        this.judulFilm = judulFilm;
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

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    String judulFilm, deskripsi, Tanggal, vote, popular, subtitle, Poster;




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.Poster);
    parcel.writeString(this.deskripsi);
    parcel.writeString(this.judulFilm);
    parcel.writeString(this.popular);
    parcel.writeString(this.subtitle);
    parcel.writeString(this.vote);
    parcel.writeString(this.Tanggal);
    }

    public FilmModel(){

    }

    public FilmModel(Cursor cursor){
        this.id = DatabaseContract.getColumnInt(cursor, DatabaseContract.FavouriteColumns._ID);
        this.Poster = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.POSTER);
        this.deskripsi = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.DESCRIPTION);
        this.judulFilm = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.TITLE);
        this.subtitle = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.SUBTITLE);
        this.popular = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.POPULARITY);
        this.vote = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.VOTING);
        this.Tanggal = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.DATE);
    }


}
