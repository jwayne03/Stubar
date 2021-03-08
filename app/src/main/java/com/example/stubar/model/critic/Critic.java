package com.example.stubar.model.critic;

import java.time.LocalDate;
import java.util.UUID;

public class Critic {
    private UUID idCritic;
    private String title;
    private UUID userID;
    private String comment;
    private float rate;
    private UUID offerID;
    private UUID documentID;
    private LocalDate date;

    public Critic(String idCritic, String title, String userID, String comment, float rate, String offerID, String documentID, LocalDate date) {
        if (idCritic != null) this.idCritic = UUID.fromString((idCritic));
        this.title = title;
        this.userID = UUID.fromString(userID);
        this.comment = comment;
        this.rate = rate;
        if (offerID != null) this.offerID= UUID.fromString(offerID);
        if (documentID != null) this.documentID = UUID.fromString(documentID);
        this.date = date;
    }

    public Critic() {}

    public UUID getIdCritic() {
        return idCritic;
    }

    public void setIdCritic(String idCritic) {
        this.idCritic = UUID.fromString(idCritic);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = UUID.fromString(userID);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public UUID getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = UUID.fromString(offerID);
    }

    public UUID getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = UUID.fromString(documentID);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}