package com.example.brainsoup.service;


import com.example.brainsoup.model.AddPatientInfo;
import com.example.brainsoup.model.AddPatientModel;
import com.example.brainsoup.model.UnsavedInfo;
import com.example.brainsoup.model.UnsavedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UnsavedAPI {

    @POST("/unSavedAPI")
    Call<List<UnsavedModel>> toRegister(@Body UnsavedInfo user);
}
