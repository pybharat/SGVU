package com.bharatapp.sgvu.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bharatapp.sgvu.R;
import com.bharatapp.sgvu.activities.dashboard;


public class emailLogin extends Fragment {
Button button;
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_email_login, container, false);
       button=(Button)view.findViewById(R.id.login2);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(getActivity().getApplicationContext(), dashboard.class);
               startActivity(i);
           }
       });
        return view;
    }
}