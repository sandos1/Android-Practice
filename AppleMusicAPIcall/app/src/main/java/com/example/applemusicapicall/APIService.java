package com.example.applemusicapicall;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("search?term=classick&amp;media=music&amp;entity=song&amp;limit=50")
    Call<Music> getAllMusic();

    @GET("search")
    Call<Music> searchbyArtistName(@Query("term") String term);
}
