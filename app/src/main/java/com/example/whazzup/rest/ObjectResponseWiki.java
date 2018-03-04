package com.example.whazzup.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrei on 15.09.2017.
 */

public class ObjectResponseWiki {
    @SerializedName("summary")
    String summary;

    @SerializedName("title")
    String title;

    @SerializedName("url")
    String url;
}
