package com.example.stubar.model.local;

import com.google.gson.annotations.SerializedName;

public class LocalApiResponse {
    @SerializedName("local")
    Local local;

    public LocalApiResponse() {
    }

    public Local getLocal() {
        return local;
    }
}
