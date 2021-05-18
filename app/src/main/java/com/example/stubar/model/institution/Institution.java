package com.example.stubar.model.institution;

import java.util.UUID;

public class Institution {
    private UUID idInstitution;
    private String name;
    private String postcode;

    //Hint contructor
    public Institution() {
        this.name = "Institution";
    }

    public UUID getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(String idInstitution) {
        this.idInstitution = UUID.fromString(idInstitution);
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
