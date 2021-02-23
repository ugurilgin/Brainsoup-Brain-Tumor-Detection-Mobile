package com.example.brainsoup.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("name")
    public String name;
    @SerializedName("surname")
    public String surname;
    @SerializedName("email")
    public String email;
    @SerializedName("key")
    public String userKey;
    @SerializedName("error")
    public  String error;

    public String getName() {
        return name;
    }

    public void setName(String result) {
        this.name = result;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String result) {
        this.surname = result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String result) {
        this.email = result;
    }
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String result) {
        this.userKey = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String result) {
        this.error = result;
    }
}
