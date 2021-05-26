package com.example.stubar.model.user;

import com.google.gson.annotations.SerializedName;


public class UserApiResponse {
    @SerializedName("user")
    private User user;

    public UserApiResponse() {
    }

    public User getUser() {
        return user;
    }
}
