package com.example.stubar.model.topic;



import java.util.UUID;

public class Topic {
    private UUID idTopic;
    private String description;

    public Topic() {
        this.description = "Enter your field study";
    }

    public Topic(String idTopic, String description) {
        this.idTopic = UUID.fromString(idTopic);
        this.description = description;
    }

    public UUID getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(String idTopic) {
        this.idTopic = UUID.fromString(idTopic);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
