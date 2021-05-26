package com.example.stubar.model.local;

import com.example.stubar.utils.deserializer.UUIDDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.UUID;


public class Local {
    @JsonDeserialize(as = UUIDDeserializer.class)
    private UUID idLocal;
    private String name;
    private int postcode;
    private float geolat;
    private float geolong;


    public Local(String idLocal, String name, int postcode, float geolat, float geolong) {
        if (idLocal != null) this.idLocal = UUID.fromString(idLocal);
        this.name = name;
        this.postcode = postcode;
        this.geolat = geolat;
        this.geolong = geolong;
    }

    public Local() {
        this.name = "Search a local";
    }

    public UUID getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = UUID.fromString(idLocal);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public float getGeolat() {
        return geolat;
    }

    public void setGeolat(float geolat) {
        this.geolat = geolat;
    }

    public float getGeolong() {
        return geolong;
    }

    public void setGeolong(float geolong) {
        this.geolong = geolong;
    }

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