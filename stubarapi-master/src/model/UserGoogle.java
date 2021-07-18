package model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGoogle {
	private UUID idUser;
	private String username;
	private String password;
	private String email;
	private String name;
	public UserGoogle(@JsonProperty("idUser")UUID idUser, 
			  @JsonProperty("username")String username, 
			  @JsonProperty("password")String password, 
			  @JsonProperty("email")String email, 
			  @JsonProperty("name")String name) {
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
	}
	
	public UUID getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = UUID.fromString(idUser);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
