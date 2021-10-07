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
    /*    for (i = 1; i <= 10; i++)
        {
            notice_data l = new notice_data();
            l.setNid("1");
            l.setNtitle("RENEWAL OF REGISTRATION / EXTENSION OF Ph.D. DURATION, OF EXISTING Ph.D. SCHOLARS");
            l.setNshort_des("Notification regarding RENEWAL OF REGISTRATION / EXTENSION OF Ph.D. DURATION, OF EXISTING Ph.D. SCHOLARS.\n" +
                    "Thanks.");
            l.setNfull_des("The essential conditions for renewal of registration/extension of Ph.D. scholars are  notified as follows: \n" +
                    "1. The scholar should have passed the course work examination as per UGC & SGVU  norms. \n" +
                    "2. Maximum duration of six years for Ph.D. has elapsed but research work is not  completed due to genuine reasons. \n" +
                    "3. For extension, No Dues Certificate (in particular from Finance Department; i.e. all  dues of previous duration must be cleared) must be submitted to Dean (Research). 4. In view of the COVID situation, the extension may be given with the conditions as  follows: \n" +
                    "(a) Case1: The research scholar has completed his/her course work and the six year duration has been over without doing any work \n" +
                    "• The extension of three- year duration (maximum) may be approved to do  research on latest technology/a new current topic with the help of supervisor and  will be finalized after the approval of RAC and DRC whereas the minimum period  for submission of the thesis will be two years. \n" +
                    "(b) Case2: The research scholar has completed his/her course work and the six year duration has been passed with some work & published at least one paper in  UGC/SCOPUS/WOS indexed journal on existing title & with the Supervisor name  provided by SGVU \n" +
                    "• The extension of three-year duration (maximum) may be approved to complete  their research on the same topic/as per the recommendation of DRC whereas the  minimum period for submission of the thesis will be one year.  \n" +
                    "(c) Case3: The research scholar is failed to clear his course work and the six-year duration has been passed \n" +
                    "• No extension will be given. The scholar may apply for re-admission as a fresh  candidate and all finance conditions will be applicable like a fresh admission.\n" +
                    "Page 1 of 2 \n" +
                    "5. The fee for extension is proposed as follows: \n" +
                    "Extension Fee: INR 120,000 (may be given in two installments)  \n" +
                    "6. In addition; \n" +
                    "• Date of admission will remain same for case 1 & 2. \n" +
                    "• After getting approval of DRC, the student will get extension and revised letter  will be issued to him/her.  \n" +
                    "7. This extension under mercy chance will stand only for 3 months from the date of  issuing the notification. After this period no student will be entertained for the same. 8. Research Department will ensure to send the information to all concerned scholars  within a time frame and extension will be given after the approval of the President. (Bears approval of the Competent Authority). \n" +
                    "[Madhusudan Sharma] \n" +
                    "Registrar \n" +
                    "Addressed to all concerned [Team SGVU]: \n" +
                    "1. All Academic Heads, Deans, Directors, Principals, Vice-Principals, HODs, Faculties 2. The CFAO / Controller of Examination  \n" +
                    "3. Dy. Director DE/Chief Proctor / Chief Librarian / T & P Cell \n" +
                    "4. Dy. Reg. (A&L / A&R /I.O.) /I/c Admissions / Finance Manager \n" +
                    "5. Director Sports / Chief Warden, Hostels/ Estate Manager / Campus Manager  6. Asstt. Reg. (A&A)/ Asstt. Reg. (HR) / OSD to President / SIO \n" +
                    "7. Officer Incharge - University Website– for necessary action. \n" +
                    "8. PS to Registrar for inclusion in the agenda of AC & BoM \n" +
                    "CC for kind information:  \n" +
                    "1. Hon’ble President \n" +
                    "2. Hon’ble Pro- President (Acad.) \n" +
                    "Bcc:  \n" +
                    "1. PS to Hon’ble Chairperson \n" +
                    "2. PS to Hon’ble Chief Mentor \n" +
                    "[Madhusudan Sharma] \n" +
                    "Registrar\n");
            l.setImg_url("https://seekho.live/bharat-sir/slider/h2.png");
            l.setDate1("3-Sep-2021");
            list1s.add(l);
            rcv.setAdapter(new myadaptar(getActivity(), list1s));
        }*/
        return view;
    }

    private void notice_api() throws JSONException {
        sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        int userid=sharedPreferences.getInt(KEY_USERID,0);
        String token=sharedPreferences.getString(KEY_TOKEN,null);

        auth auth=new auth();
        if(userid != 0 || token!=null)
        {
            auth.setId(userid);
            auth.setToken(token);
        }
        else
        {
            Toast.makeText(getActivity(), "Login Again", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getActivity(), login.class);
            startActivity(i);
        }
        allnotice a1=new allnotice();
        a1.setAuth(auth);
        Gson gson = new Gson();
        String json = gson.toJson(a1);
        JSONObject jsonObject=new JSONObject(json);
retrofitClient.getWebService().notice_call(jsonObject).enqueue(new Callback<String>() {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful())
        {
            try {
                JSONObject jsonObject2=new JSONObject(response.body());
                Toast.makeText(getActivity(), jsonObject2.toString(), Toast.LENGTH_SHORT).show();
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
                        String img=jsonObject1.getString("img_url");
                        if(img=="no")
                        {
                            l.setImg_url("https://seekho.live/bharat-sir/slider/h2.png");
                        }
                        else
                        {
                            l.setImg_url(img);
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