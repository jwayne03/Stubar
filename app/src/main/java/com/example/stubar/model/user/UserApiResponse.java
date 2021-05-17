package com.example.stubar.model.user;

import com.example.stubar.model.document.Document;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserApiResponse {
    @SerializedName("user")
    User user;

    public UserApiResponse() {
    }

    public User getUser() {
        return user;
    }
}
