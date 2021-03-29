package com.example.brainsoup.service;


import com.example.brainsoup.model.UnsavedInfo;
import com.example.brainsoup.model.UnsavedModel;
import com.example.brainsoup.model.UnsavedSaveInfo;
import com.example.brainsoup.model.UnsavedSaveModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UnsavedSaveAPI {

    @POST("/unSavedSaveAPI")
    Call<List<UnsavedSaveModel>> toRegister(@Body UnsavedSaveInfo user);
}
