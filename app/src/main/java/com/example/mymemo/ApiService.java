package com.example.mymemo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("catatans.json")
    Call<Map<String,Catatan>> getCatatan();

    @POST("catatans.json")
    Call<Void> addLapangan(@Body Catatan catatan);

    @PUT("catatans/{id}.json")
    Call<Void> updateLapangan(@Path("id") String id, @Body Catatan catatan);

    @DELETE("catatans/{id}.json")
    Call<Void> deleteLapangan(@Path("id") String id);
}
