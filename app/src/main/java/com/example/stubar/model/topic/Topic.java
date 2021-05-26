package com.example.stubar.model.topic;

public class Topic {

    private String uuid;
    private String description;

    public Topic() {
        this.description = "Enter your field study";
    }

    public Topic(String uuid, String description) {
        this.uuid = uuid;
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
