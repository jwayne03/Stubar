package com.example.stubar.model.offer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferApiResponse {
    @SerializedName("offers")
    private final List<Offer> offers;

    /**
     * Default constructor
     * @param offers
     */
    public OfferApiResponse(List<Offer> offers) {
        this.offers = offers;
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