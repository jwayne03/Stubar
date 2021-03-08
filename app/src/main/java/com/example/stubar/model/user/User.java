package com.example.stubar.model.user;

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
    private LocalDate birthday;
    private String institution;

    public User() {
    }

    public User(String idUser, String username, String password, String name, String email,
                String surname, String profilePhoto, LocalDate birthday, String institution) {
        if (idUser != null) this.idUser = UUID.fromString(idUser);
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.profilePhoto = profilePhoto;
        this.birthday = birthday;
        this.institution = institution;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
