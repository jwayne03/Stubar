package com.example.stubar.model.user;

import com.google.gson.annotations.SerializedName;


public class UserApiResponse {
    @SerializedName("user")
    private User user;

    /**
     * Default constructor of UserApiResponse
     */
    public UserApiResponse() {
    }

    /**
     * Get the user
     *
     * @return User
     */
    public User getUser() {
        return user;
    }
}
