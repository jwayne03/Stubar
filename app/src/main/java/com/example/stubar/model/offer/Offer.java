package com.example.stubar.model.offer;


import java.time.LocalDate;
import java.util.UUID;

public class Offer {
    private UUID idOffer;
    private String imageOffer;
    private String comment;
    private double price;
    private UUID localID;
    private UUID userID;
    private LocalDate date;

    public Offer(String idOffer, String imageOffer, String comment, double price, String localID, String userID, LocalDate date) {
        if (idOffer != null) this.idOffer = UUID.fromString(idOffer);
        this.imageOffer = imageOffer;
        this.comment = comment;
        this.price = price;
        this.localID = UUID.fromString(localID);
        this.userID = UUID.fromString(userID);
        this.date = date;

    }

    public Offer() {
    }

    public UUID getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = UUID.fromString(idOffer);
    }

    public String getImageOffer() {
        return imageOffer;
    }

    public void setImageOffer(String imageOffer) {
        this.imageOffer = imageOffer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getLocalID() {
        return localID;
    }

    public void setLocal(String localID) {
        this.localID = UUID.fromString(localID);
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = UUID.fromString(userID);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}