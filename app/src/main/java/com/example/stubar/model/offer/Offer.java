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

    private String localName;

    /**
     * Constructor of Offer
     *
     * @param idOffer    String
     * @param imageOffer String
     * @param comment    String
     * @param price      double
     * @param localID    String
     * @param userID     String
     * @param date       LocalDate
     * @param localName  String
     */
    public Offer(String idOffer, String imageOffer, String comment, double price, String localID, String userID, LocalDate date, String localName) {
        if (idOffer != null) this.idOffer = UUID.fromString(idOffer);
        this.imageOffer = imageOffer;
        this.comment = comment;
        this.price = price;
        this.localID = UUID.fromString(localID);
        this.userID = UUID.fromString(userID);
        this.date = date;
        this.localName = localName;
    }

    /**
     * Default Constructor of Offer
     */
    public Offer() {
    }

    // GETTERS & SETTERS

    /**
     * Get the id of the offer
     *
     * @return UUID
     */
    public UUID getIdOffer() {
        return idOffer;
    }

    /**
     * Set the id of the offer
     *
     * @param idOffer String
     */
    public void setIdOffer(String idOffer) {
        this.idOffer = UUID.fromString(idOffer);
    }

    /**
     * Get the image offer
     *
     * @return String
     */
    public String getImageOffer() {
        return imageOffer;
    }

    /**
     * Set the image offer
     *
     * @param imageOffer String
     */
    public void setImageOffer(String imageOffer) {
        this.imageOffer = imageOffer;
    }

    /**
     * Get the comment of the offer
     *
     * @return String
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment of the offer
     *
     * @param comment String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get the price of the offer
     *
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the offer
     *
     * @param price double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the local ID
     *
     * @return UUID
     */
    public UUID getLocalID() {
        return localID;
    }

    /**
     * Set local offer
     *
     * @param localID String
     */
    public void setLocal(String localID) {
        this.localID = UUID.fromString(localID);
    }

    /**
     * Get the ID of the user
     *
     * @return
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * Set the user ID
     *
     * @param userID String
     */
    public void setUserID(String userID) {
        this.userID = UUID.fromString(userID);
    }

    /**
     * Get the date of the offer
     *
     * @return LocalDate
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the date of the Offer
     *
     * @param date LocalDate
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the localname
     *
     * @return String
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Set the localname of the offer
     *
     * @param localName String
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    /**
     * Method to show in console all the information of the offer
     *
     * @return
     */
    @Override
    public String toString() {
        return "Offer{" +
                ", imageOffer='" + imageOffer + '\'' +
                ", comment='" + comment + '\'' +
                ", price=" + price +
                ", localID=" + localID +
                ", userID=" + userID +
                ", date=" + date +
                '}';
    }
}