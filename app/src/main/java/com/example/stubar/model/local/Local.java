package com.example.stubar.model.local;

import java.util.UUID;


public class Local {
    private UUID idLocal;
    private String name;
    private int postcode;
    private float geolat;
    private float geolong;


    /**
     * Constructor of Local
     *
     * @param idLocal  String
     * @param name     String
     * @param postcode int
     * @param geolat   float
     * @param geolong  float
     */
    public Local(String idLocal, String name, int postcode, float geolat, float geolong) {
        if (idLocal != null) this.idLocal = UUID.fromString(idLocal);
        this.name = name;
        this.postcode = postcode;
        this.geolat = geolat;
        this.geolong = geolong;
    }

    /**
     * Default constructor of local and has a hint to show to the UI
     */
    public Local() {
        this.name = "Search a local";
    }

    // GETTERS AND SETTERS

    /**
     * Get the id of the local
     *
     * @return UUID
     */
    public UUID getIdLocal() {
        return idLocal;
    }

    /**
     * Set the id of the local
     *
     * @param idLocal String
     */
    public void setIdLocal(String idLocal) {
        this.idLocal = UUID.fromString(idLocal);
    }

    /**
     * Get the name of the local
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the local
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * GEt the postcode of the local
     *
     * @return int
     */
    public int getPostcode() {
        return postcode;
    }

    /**
     * Set the postcode of the local
     *
     * @param postcode int
     */
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    /**
     * Get the latitude of the local
     *
     * @return float
     */
    public float getGeolat() {
        return geolat;
    }

    /**
     * Set the latitude of the local
     *
     * @param geolat float
     */
    public void setGeolat(float geolat) {
        this.geolat = geolat;
    }

    /**
     * Get the longitude of the local
     *
     * @return float
     */
    public float getGeolong() {
        return geolong;
    }

    /**
     * Set the longitude of the local
     *
     * @param geolong float
     */
    public void setGeolong(float geolong) {
        this.geolong = geolong;
    }


    /**
     * Method to show all the data of local
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Local{" +
                "idLocal=" + idLocal +
                ", name='" + name + '\'' +
                ", postcode=" + postcode +
                ", geolat=" + geolat +
                ", geolong=" + geolong +
                '}';
    }
}