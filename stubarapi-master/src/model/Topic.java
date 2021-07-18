package model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Topic {
	private UUID idTopic;
	private String description;
	
	public Topic() {}
	
	@JsonCreator
	public Topic(@JsonProperty("idTopic") UUID idTopic, 
				@JsonProperty("description") String description) {
		super();
		this.idTopic = idTopic;
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
