package com.example.gregorio.moviecatalouge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllItems {

    @SerializedName("result")
    @Expose
    private List<FilmItems> results = null;

    public List<FilmItems> getResults() {
        return results;
    }

    public void setResults(List<FilmItems> results){
        this.results = results;
    }
}
