package com.example.brainsoup.service;


import com.example.brainsoup.model.RegisterInfo;
import com.example.brainsoup.model.RegisterModel;
import com.example.brainsoup.model.UpdateInfo;
import com.example.brainsoup.model.UpdateModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UpdateAPI {

    @POST("/updateAPI")
    Call<List<UpdateModel>> toRegister(@Body UpdateInfo user);
}
