package com.example.whazzup.rest;

class ObjectRequestPush {
    private String request;

    ObjectRequestPush(String init_request) {
        request = init_request;
    }

    static class Twitter {
        private String location;
        Twitter(String init_request) {
            location = init_request;
        }
    }
}