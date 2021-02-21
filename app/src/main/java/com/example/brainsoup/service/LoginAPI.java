package com.example.brainsoup.service;

import com.example.brainsoup.model.LoginInfo;
import com.example.brainsoup.model.LoginModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginAPI {
    @POST("/loginAPI")
    Call<List<LoginModel>> toLogin( @Body LoginInfo user);
}
