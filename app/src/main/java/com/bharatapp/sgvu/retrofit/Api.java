package com.bharatapp.sgvu.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("Auth/register")
    Call<String> register(
            @Body JsonObject jsonObject
    );
    @POST("Auth/verifyotp")
    Call<String> verifyotp(
            @Body JsonObject jsonObject
    );

    @POST("Auth/resendotp")
    Call<String> resendotp(
            @Body JsonObject jsonObject
    );
}
