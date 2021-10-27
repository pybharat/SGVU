package com.bharatapp.sgvu.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("Auth/login")
    Call<String> loginApi(
            @Body JsonObject jsonObject
    );
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
    @POST("Auth/forgotpassword")
    Call<String> forgotpass(
            @Body JsonObject jsonObject
    );
    @POST("Auth/verifyforgototp")
    Call<String> verifyforgototp(
            @Body JsonObject jsonObject
    );
    @POST("Auth/resendforgototp")
    Call<String> resendforgototp(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/getnotices")
    Call<String> notice_call(
            @Body JsonObject jsonObject
    );

    @POST("Dashboard/updatenoticeimg")
    Call<String> updatenoticeimg(
            @Body JsonObject jsonObject
    );

    @POST("Dashboard/insertnotice")
    Call<String> insertnotice(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/updateposterimg")
    Call<String> updateposterimg(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/insertposter")
    Call<String> insertposter(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/getposter")
    Call<String> getposter(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/updatenotice")
    Call<String> updatenotice(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/updateprofileimg")
    Call<String> updateprofileimg(
            @Body JsonObject jsonObject
    );
    @POST("Dashboard/getuserinfo")
    Call<String> getuserinfo(
            @Body JsonObject jsonObject
    );
}
