package com.example.stubar.model.user;

import android.os.Build;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID idUser;
    private String username;
    private String password;
    private String name;
    private String email;
    private String surname;
    private String profilePhoto;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
    private String institution;

    /**
     * Default constructor of the User
     */
    public User() {
    }

    // GETTERS & SETTERS

    /**
     * Get the id of the user
     * @return UUID
     */
    public UUID getIdUser() {
        return idUser;
    }

    /**
     * Set the id of the user
     * @param idUser String
     */
    public void setIdUser(String idUser) {
        this.idUser = UUID.fromString(idUser);
    }

    /**
     * Get the user username
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of the user
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the name of the user
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the user
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the email of the user
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the Email of the user
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the surname of the user
     * @return String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set the surname of the user
     * @param surname String
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get the profile photo of the user
     * @return String
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * Set the profilephoto of the user
     * @param profilePhoto String
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * Get the birthday of the user
     * @return LocalDate
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Method to set the birthday of the user and check that field is not null
     * @param birthday String
     */
    public void setBirthday(String birthday) {
        if(birthday != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String[] date = birthday.split("-");
                this.birthday = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
            }
        } else {
            this.birthday = null;
        }
    }

    /**
     * Get the institution of the user
     * @return String
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Set the institution of the user
     * @param institution String
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", birthday=" + birthday +
                ", institution='" + institution + '\'' +
                '}';
    }
}