package com.example.brainsoup.service;


import com.example.brainsoup.model.AddPatientInfo;
import com.example.brainsoup.model.AddPatientModel;
import com.example.brainsoup.model.UpdateInfo;
import com.example.brainsoup.model.UpdateModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddPatientAPI {

    @POST("/addPatientAPI")
    Call<List<AddPatientModel>> toRegister(@Body AddPatientInfo user);
}
