package com.example.whazzup.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PushContentService {

    @POST("yo_momma_joke_push")
    Call<String> loadJokePush();

    @POST("chuck_norris_push")
    Call<String> loadChuckNorrisPush();

    @POST("giphy_push")
    Call<String> loadGiphyPush(@Body ObjectRequestPush request);

    @POST("forismatic_push")
    Call<ObjectResponseForismatic> loadForismaticPush();

    @POST("wikipedia_push")
    Call<String> loadWikiPush(@Body ObjectRequestPush request);

    @POST("twitter_push")
    Call<ObjectResponseTwitter> loadTwitterPush(@Body ObjectRequestPush.Twitter request);
}