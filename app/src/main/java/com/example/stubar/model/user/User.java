package com.example.stubar.model.user;

import android.os.Build;
import android.util.Log;

import com.example.stubar.utils.deserializer.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import org.json.JSONException;
import org.json.JSONObject;

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
//    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;
    private String institution;

    public User() {}

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

    public void setBirthday(String birthday) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String[] date = birthday.split("-");
            this.birthday = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
        }
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        try {
            jo.put("username", this.username);
            jo.put("password", this.password);
            jo.put("name", this.name);
            jo.put("surname", this.surname);
            jo.put("email", this.email);
            jo.put("profilePhoto", this.profilePhoto);
            jo.put("birthday", this.birthday.toString());
            jo.put("institution", this.institution);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSONOBJECT", "-" + jo.toString());
        return jo;
    }
}
