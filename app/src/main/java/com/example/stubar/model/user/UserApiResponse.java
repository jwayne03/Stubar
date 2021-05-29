package com.example.stubar.model.user;

import com.google.gson.annotations.SerializedName;

public class UserApiResponse {
    @SerializedName("user")
    private final User user;

    /**
     * Default constructor of UserApiResponse
     * @param user
     */
    public UserApiResponse(User user) {
        this.user = user;
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