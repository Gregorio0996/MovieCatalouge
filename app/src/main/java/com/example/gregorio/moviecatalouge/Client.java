package com.example.gregorio.moviecatalouge;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Client {
    @GET("3/search/movie")
    Call<FilmItems> getList(@Query("api_key") String api_key, @Query("language") String language, @Query("query") String query);

    @GET("3/movie/{mov_id}")
    Call<FilmItems> getDetail(@Path("mov_id") String mov_id, @Query("api_key") String api_key);

    @GET("3/movie/upcoming")
    Call<AllItems> getUpcoming(@Query("api_key") String api_key, @Query("language") String language);

    @GET("3/movie/now_playing")
    Call<FilmItems> getPlaying(@Query("api_key") String api_key, @Query("language") String language);
}
