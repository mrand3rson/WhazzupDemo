package com.example.whazzup.rest;


import com.google.gson.annotations.SerializedName;

class ObjectResponseTwitter {
    @SerializedName("profile_url")
    String profileUrl;

    @SerializedName("name")
    String name;

    @SerializedName("text")
    String text;
}
