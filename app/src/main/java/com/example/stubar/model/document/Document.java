package com.example.stubar.model.document;

import java.time.LocalDate;
import java.util.UUID;


public class Document {
    private UUID idDocument;
    private String name;
    private String docPath;
    private double rate;
    private int grade;
    private UUID userID;
    private LocalDate date;
    private UUID topicID;

    //Only set for get request
    private String username;
    private String topicName;


    public Document(String idDocument, String name, String docPath, double rate, int grade, String userID, LocalDate date, String topicID, String username, String topicName) {
        if (idDocument != null) this.idDocument = UUID.fromString(idDocument);
        this.name = name;
        this.docPath = docPath;
        this.rate = rate;
        this.grade = grade;
        this.userID = UUID.fromString(userID);
        this.date = date;
        this.topicID = UUID.fromString(topicID);
        this.username = username;
        this.topicName = topicName;
    }

    public Document() {
    }

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

    public void setRate(Double rate) {
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

    public String getUsername() { return username; }

    public String getTopicName() { return topicName; }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}