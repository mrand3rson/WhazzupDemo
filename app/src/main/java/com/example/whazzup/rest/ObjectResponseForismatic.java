package com.example.whazzup.rest;

import com.google.gson.annotations.SerializedName;


class ObjectResponseForismatic {
    @SerializedName("text")
    String what;

    @SerializedName("name")
    String who;
}