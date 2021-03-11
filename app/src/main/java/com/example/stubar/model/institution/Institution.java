package com.example.stubar.model.institution;

import java.util.UUID;

public class Institution {
    private UUID id_Institution;
    private String name;
    private String postcode;

    public UUID getId_Institution() {
        return id_Institution;
    }

    public void setId_Institution(UUID id_Institution) {
        this.id_Institution = id_Institution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
