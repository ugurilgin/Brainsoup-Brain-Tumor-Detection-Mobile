package com.example.brainsoup.service;


import com.example.brainsoup.model.RegisterInfo;
import com.example.brainsoup.model.RegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterAPI {

    @POST("/registerAPI")
    Call<List<RegisterModel>> toRegister(@Body RegisterInfo user);
}
