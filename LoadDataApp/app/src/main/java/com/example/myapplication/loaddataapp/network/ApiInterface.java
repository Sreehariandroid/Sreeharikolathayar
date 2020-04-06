package com.example.myapplication.loaddataapp.network;

import com.example.myapplication.loaddataapp.model.MainElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("s/2iodh4vg0eortkl/facts.js")
    Call<MainElement> getData();
}
