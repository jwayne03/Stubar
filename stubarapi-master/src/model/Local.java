package model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Local {
	private UUID idLocal;
	private String name;
	private int postcode;
	private float geolat;
	private float geolong;
	
	@JsonCreator  
	public Local(@JsonProperty("idLocal")String idLocal, 
			@JsonProperty("name")String name,
			@JsonProperty("postcode")int postcode,
			@JsonProperty("geolat")float geolat,
			@JsonProperty("geolong")float geolong) {
		if (idLocal != null) {
			this.idLocal = UUID.fromString(idLocal);
		}
		this.name = name;
		this.postcode = postcode;
		this.geolat = geolat;
		this.geolong = geolong;
	}

	public Local() {}

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

	
	
	
	
}
