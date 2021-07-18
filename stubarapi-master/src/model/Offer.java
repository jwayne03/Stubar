package model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.deserializer.LocalDateDeserializer;

public class Offer {
	private UUID idOffer;
	private String imageOffer;
	private String comment;
	private double price;
	private UUID localID;
	private UUID userID;
	@JsonDeserialize(using=LocalDateDeserializer.class)
	private LocalDate date;
	
	//Only return
	private String localName;
	
	@JsonCreator
	public Offer(@JsonProperty("idOffer")String idOffer, 
			@JsonProperty("imageOffer")String imageOffer, 
			@JsonProperty("comment")String comment, 
			@JsonProperty("price")double price, 
			@JsonProperty("localID")String localID, 
			@JsonProperty("userID")String userID, 
			@JsonProperty("date")LocalDate date) {
		if (idOffer != null) {
			this.idOffer = UUID.fromString(idOffer);
		}
		this.imageOffer = imageOffer;
		this.comment = comment;
		this.price = price;
		if (localID != null) {
			this.localID = UUID.fromString(localID);
		}
		if (userID != null) {
			this.userID = UUID.fromString(userID);
		}
		this.date = date;
		this.localName = null;
	}

	public Offer() {}

	public UUID getIdOffer() {
		return idOffer;
	}

	public void setIdOffer(String idOffer) {
		if (idOffer != null) {
			this.idOffer = UUID.fromString(idOffer);
		} else {
			this.idOffer = null;
		}
		
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

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}
		
	
}
