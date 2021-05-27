package com.example.stubar.model.offer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferApiResponse {
    @SerializedName("offers")
    private List<Offer> offers;

    /**
     * Default constructor
     */
    public OfferApiResponse() {
    }

    /**
     * Get all the offers
     *
     * @return List<Offer>
     */
    public List<Offer> getOffers() {
        return offers;
    }
}
