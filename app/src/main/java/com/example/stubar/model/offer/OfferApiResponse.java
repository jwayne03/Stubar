package com.example.stubar.model.offer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferApiResponse {
    @SerializedName("offers")
    List<Offer> offers;

    public OfferApiResponse(List<Offer> offers) {
        this.offers = offers;
    }

    public OfferApiResponse() {
    }

    public List<Offer> getOffers() {
        return offers;
    }
}
