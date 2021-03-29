package com.example.brainsoup.model;

import com.google.gson.annotations.SerializedName;

public class AddPatientModel {
    @SerializedName("result")
    public String result;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
