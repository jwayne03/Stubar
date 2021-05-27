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


    /**
     * Constructor of the class Document
     *
     * @param idDocument UUID
     * @param name       String
     * @param docPath    String
     * @param rate       double
     * @param grade      int
     * @param userID     String
     * @param date       LocalDate
     * @param topicID    String
     * @param username   String
     * @param topicName  String
     */
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

    /**
     * Default constructor of Document
     */
    public Document() {
    }

    // GETTERS & SETTERS

    /**
     * Get the id of the document
     *
     * @return UUID
     */
    public UUID getIdDocument() {
        return idDocument;
    }

    /**
     * Set the ID of the document
     *
     * @param idDocument String
     */
    public void setIdDocument(String idDocument) {
        this.idDocument = UUID.fromString(idDocument);
    }

    /**
     * Get name of the user has uploaded the document
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the user has uploaded the document
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the path of the document
     *
     * @return String
     */
    public String getDocPath() {
        return docPath;
    }

    /**
     * Set the path of the document
     *
     * @param docPath String
     */
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    /**
     * Get the rate of the document
     *
     * @return double
     */
    public double getRate() {
        return rate;
    }

    /**
     * Set the rate of the document
     *
     * @param rate Double
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }

    /**
     * Get the grade of the document
     *
     * @return int
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Set the grade of the document
     *
     * @param grade int
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Get the user ID
     *
     * @return UUID
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * Set the user id
     *
     * @param userID UUID
     */
    public void setUserID(String userID) {
        this.userID = UUID.fromString(userID);
    }

    /**
     * Get the localdate
     *
     * @return LocalDate
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the date of the document
     *
     * @param date LocalDate
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the id of the topic
     *
     * @return UUID
     */
    public UUID getTopicID() {
        return topicID;
    }

    /**
     * Set the id of the topic
     *
     * @param topicID String
     */
    public void setTopicID(String topicID) {
        this.topicID = UUID.fromString(topicID);
    }

    /**
     * Set the username of the user
     *
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the username of the user
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the topicname of the document
     *
     * @return String
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * Set the topicname of the document
     *
     * @param topicName String
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}