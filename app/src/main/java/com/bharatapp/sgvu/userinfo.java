package com.bharatapp.sgvu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.bharatapp.sgvu.activities.login;
import com.bharatapp.sgvu.model_class.userinfo_data;
import com.bharatapp.sgvu.retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userinfo {
    int userid;
    Context ct;
    public userinfo_data userinfo_data;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "sgvu";
    private static final String KEY_USERID = "userid";
    private static final String KEY_TOKEN = "token";
    RetrofitClient retrofitClient;
    public userinfo(int userid, Context ct) {
        this.userid = userid;
        this.ct = ct;
    }

    public userinfo_data getuserinfo() {
        sharedPreferences = ct.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int userid = sharedPreferences.getInt(KEY_USERID, 0);
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        JsonObject auth = new JsonObject();
        userinfo_data = new userinfo_data();
        if (userid != 0 || token != null) {
            auth.addProperty("id", userid);
            auth.addProperty("token", token);
        } else {
            Toast.makeText(ct, "Login Again", Toast.LENGTH_SHORT).show();
        }
        JsonObject user = new JsonObject();
        user.addProperty("userid", userid);
        user.add("auth", auth);

        retrofitClient = new RetrofitClient();
        retrofitClient.getWebService().getuserinfo(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    try {
                        JSONObject obj = new JSONObject(response.body());
                        if (Integer.parseInt(obj.get("code").toString()) == 200) {
                            userinfo_data.setUser_firstname(obj.getString("user_firstname"));
                            userinfo_data.setUser_email(obj.getString("user_email"));
                            userinfo_data.setProfile_image(obj.getString("profile_image"));
                            userinfo_data.setUser_mobile(obj.getString("user_mobile"));
                            Log.d("bharat123",userinfo_data.getUser_firstname());
                        } else if (Integer.parseInt(obj.get("code").toString()) == 400) {
                            Toast.makeText(ct, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.d("bharat123",obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(ct, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d("bharat123","Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ct, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return userinfo_data;
    }

}
