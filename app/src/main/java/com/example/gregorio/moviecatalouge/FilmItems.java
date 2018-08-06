package com.example.gregorio.moviecatalouge;


import org.json.JSONObject;

import java.io.Serializable;

public class FilmItems implements Serializable {


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


}
