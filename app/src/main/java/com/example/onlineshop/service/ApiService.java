package com.example.onlineshop.service;

import com.example.onlineshop.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/products/")
    Call<List<Example>> getAllExample();

}
