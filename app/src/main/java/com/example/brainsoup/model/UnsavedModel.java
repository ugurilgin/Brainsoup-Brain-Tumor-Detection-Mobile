package com.example.brainsoup.model;

import com.google.gson.annotations.SerializedName;

public class UnsavedModel {
    @SerializedName("result")
    public String result;
    public String imgloc;
    public String tumorloc;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getImgloc() {
        return imgloc;
    }

    public void setImgloc(String result) {
        this.imgloc = imgloc;
    }

    public String getTumorloc() {
        return tumorloc;
    }

    public void setTumorloc(String result) {
        this.tumorloc = tumorloc;
    }

}
