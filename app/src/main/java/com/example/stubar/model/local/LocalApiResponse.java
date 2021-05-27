package com.example.stubar.model.local;

import com.google.gson.annotations.SerializedName;

public class LocalApiResponse {
    @SerializedName("local")
    Local local;

    /**
     * Default constructor
     */
    public LocalApiResponse() {
    }

    /**
     * Get local
     *
     * @return Local
     */
    public Local getLocal() {
        return local;
    }
}
