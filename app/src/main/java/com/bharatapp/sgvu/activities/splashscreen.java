package com.bharatapp.sgvu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bharatapp.sgvu.R;
import com.bharatapp.sgvu.activities.login;


public class splashscreen extends AppCompatActivity {
    private static int SPLASE_TIME_OUT=2700;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(), login.class);
                startActivity(i);
                finish();
            }
        },SPLASE_TIME_OUT);
    }
    }
