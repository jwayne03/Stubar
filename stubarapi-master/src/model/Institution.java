package model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Institution {
	private UUID idInstitution;
	private String name;
	private int postcode;
	
	public Institution() {}
	
	@JsonCreator
	public Institution(@JsonProperty("idInstitution") String idInstitution, 
						@JsonProperty("name") String name, 
						@JsonProperty("postcode") int postcode) {
		super();
		this.idInstitution = UUID.fromString(idInstitution);
		this.name = name;
		this.postcode = postcode;
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
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	
}
