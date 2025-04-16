package com.example.mymemo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://consumeapi-dabca-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
