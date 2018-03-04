package com.example.whazzup.rest;

class ObjectRequest {
    private int user_id;
    private String request;

    ObjectRequest(int init_user_id, String init_request) {
        user_id = init_user_id;
        request = init_request;
    }

    static class Twitter {
        private int user_id;
        private String location;

        Twitter(int init_user_id, String init_location) {
            user_id = init_user_id;
            location = init_location;
        }
    }
}
