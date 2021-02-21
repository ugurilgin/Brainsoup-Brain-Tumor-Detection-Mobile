package com.example.brainsoup.service;

import com.example.brainsoup.model.ForgetModel;
import com.example.brainsoup.model.LoginModel;
import com.example.brainsoup.view.Forget;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ForgetAPI {
    @GET//("{email}")
    Call<List<ForgetModel>> getData(@Url String url);



}
