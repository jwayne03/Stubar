package com.example.stubar.model.topic;


import java.util.UUID;

public class Topic {
    private UUID idTopic;
    private String description;

    /**
     * Default constructor with the description initialized to show the hint
     */
    public Topic() {
        this.description = "Enter your field study";
    }

    /**
     * Constructor of topic
     *
     * @param idTopic     String
     * @param description String
     */
    public Topic(String idTopic, String description) {
        this.idTopic = UUID.fromString(idTopic);
        this.description = description;
    }

    // GETTERS & SETTERS

    /**
     * Get the id of the topic
     *
     * @return UUID
     */
    public UUID getIdTopic() {
        return idTopic;
    }

    /**
     * Set the id of the topic
     *
     * @param idTopic String
     */
    public void setIdTopic(String idTopic) {
        this.idTopic = UUID.fromString(idTopic);
    }

    /**
     * Get the description of the topic
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the topic
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
