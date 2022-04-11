package com.example.ftpserver.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IFtpApi {
    @POST("/uploadFile")
    public Call<String> uploadImage(@Body String image);
}
