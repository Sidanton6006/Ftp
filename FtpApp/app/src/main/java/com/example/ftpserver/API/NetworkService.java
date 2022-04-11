package com.example.ftpserver.API;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private static final String BASE_URL = "http://10.0.2.2:7278";

    public NetworkService(){
        Gson gson = new GsonBuilder().setLenient().create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public IFtpApi getFtpApi(){
        return mRetrofit.create(IFtpApi.class);
    }
}