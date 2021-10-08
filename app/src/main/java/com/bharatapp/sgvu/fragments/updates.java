package com.bharatapp.sgvu.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bharatapp.sgvu.R;
import com.bharatapp.sgvu.activities.login;
import com.bharatapp.sgvu.adapter.myadaptar;
import com.bharatapp.sgvu.model_class.allnotice;
import com.bharatapp.sgvu.model_class.auth;
import com.bharatapp.sgvu.model_class.notice_data;
import com.bharatapp.sgvu.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class updates extends Fragment {


    RecyclerView rcv;
    List<notice_data> list1s;
    View view;
    int i;
    SharedPreferences sharedPreferences;
    private  static  final String SHARED_PREF_NAME="sgvu";
    private  static  final String KEY_USERID="userid";
    private  static  final String KEY_TOKEN="token";
    RetrofitClient retrofitClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_updates, container, false);

        list1s = new ArrayList<>();
        rcv = (RecyclerView)view.findViewById(R.id.rc1);
    rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
    retrofitClient=new RetrofitClient();
        try {
            notice_api();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void notice_api() throws JSONException {
        sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int userid=sharedPreferences.getInt(KEY_USERID,0);
        String token=sharedPreferences.getString(KEY_TOKEN,null);
        JsonObject auth=new JsonObject();

        if(userid != 0 || token!=null)
        {
            auth.addProperty("id",userid);
            auth.addProperty("token",token);
        }
        else
        {
            Toast.makeText(getActivity(), "Login Again", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getActivity(), login.class);
            startActivity(i);
        }
        JsonObject notice=new JsonObject();
        notice.add("auth",auth);
        retrofitClient.getWebService().notice_call(notice).enqueue(new Callback<String>() {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful())
        {
            try {
                JSONObject jsonObject2=new JSONObject(response.body());
                if(Integer.parseInt(jsonObject2.getString("code"))==200)
                {
                    JSONArray jsonArray= jsonObject2.getJSONArray("message");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1= jsonArray.getJSONObject(i);

                        notice_data l = new notice_data();
                        l.setNid(String.valueOf(jsonObject1.getInt("id")));
                        l.setNtitle(jsonObject1.getString("title"));
                        l.setNshort_des(jsonObject1.getString("short_des"));
                        l.setNfull_des(jsonObject1.getString("full_des"));
                        if(jsonObject1.getString("img_url")=="NO")
                        {
                            l.setImg_url("https://seekho.live/bharat-sir/slider/h2.png");

                        }
                        else
                        {
                            l.setImg_url(jsonObject1.getString("img_url"));
                        }

                        l.setDate1(String.valueOf(jsonObject1.get("created")));
                        list1s.add(l);
                        rcv.setAdapter(new myadaptar(getActivity(), list1s));
                    }
                }
                else if(Integer.parseInt(jsonObject2.getString("code"))==400)
                {
                    Toast.makeText(getActivity(),jsonObject2.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
});
    }
}