package com.example.stubar.model.user;

import com.google.gson.annotations.SerializedName;


public class UserApiResponse {
    @SerializedName("user")
    User user;

    public UserApiResponse() {
    }

    public User getUser() {
        return user;
    }
}
