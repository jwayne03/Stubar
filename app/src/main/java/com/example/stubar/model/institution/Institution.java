package com.example.stubar.model.institution;

import java.util.UUID;

public class Institution {
    private UUID idInstitution;
    private String name;
    private String postcode;

    /**
     * Default constructor with the name initialized to refer a hind in the UI
     */
    public Institution() {
        this.name = "Institution";
    }

    // GETTER AND SETTER

    /**
     * Get the id of the institution
     *
     * @return UUID
     */
    public UUID getIdInstitution() {
        return idInstitution;
    }

    /**
     * Set the ID of the institution
     *
     * @param idInstitution String
     */
    public void setIdInstitution(String idInstitution) {
        this.idInstitution = UUID.fromString(idInstitution);
    }

    /**
     * Get the name of the institution
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the institution
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the postcode of the institution to know the ubication if the institution
     *
     * @return String
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Set the postcode of the institution
     *
     * @param postcode String
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
