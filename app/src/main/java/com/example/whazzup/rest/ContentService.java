package com.example.whazzup.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ContentService {
    @POST("yo_momma_joke")
    Call<String> loadJoke(@Body ObjectRequestSimple request);

    @POST("chuck_norris")
    Call<String> loadChuckNorris(@Body ObjectRequestSimple request);

    @POST("giphy")
    Call<String> loadGiphy(@Body ObjectRequest request);

    @POST("forismatic")
    Call<ObjectResponseForismatic> loadForismatic(@Body ObjectRequestSimple request);

    @POST("wikipedia")
    Call<ObjectResponseWiki> loadWiki(@Body ObjectRequest request);

    @POST("twitter")
    Call<ObjectResponseTwitter> loadTwitter(@Body ObjectRequest.Twitter request);
}
