package model;


import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import utils.deserializer.LocalDateDeserializer;

public class Document {
	private UUID idDocument;
	private String name;
	private String docPath;
	private double rate;
	private int grade;
	private UUID userID;
	@JsonDeserialize(using=LocalDateDeserializer.class)
	private LocalDate date;
	private UUID topicID;

	//Only return
	private String username;
	private String topicName;
	
	@JsonCreator
	public Document(@JsonProperty("idDocument")String idDocument, 
			@JsonProperty("name")String name, 
			@JsonProperty("docPath")String docPath, 
			@JsonProperty("rate")double rate, 
			@JsonProperty("grade")int grade, 
			@JsonProperty("userID")String userID,
			@JsonProperty("date")LocalDate date,
			@JsonProperty("topic")String topicID) {
		if (idDocument != null) {
			this.idDocument = UUID.fromString(idDocument);
		}
		this.name = name;
		this.docPath = docPath;
		this.rate = rate;
		this.grade = grade;
		if (userID != null) {
			this.userID = UUID.fromString(userID);
		}
		this.date = date;
		if (topicID != null) {
			this.topicID = UUID.fromString(topicID);
		}
		this.username = null;
		this.topicName = null;
	}

	public Document() {}

	public UUID getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(String idDocument) {
		this.idDocument = UUID.fromString(idDocument);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
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

	public UUID getTopicID() {
		return topicID;
	}

	public void setTopicID(String topicID) {
		this.topicID = UUID.fromString(topicID);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getUsername() {
		return username;
	}

	public String getTopicName() {
		return topicName;
	}
}
