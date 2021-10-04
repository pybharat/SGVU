package com.bharatapp.sgvu.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bharatapp.sgvu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Button profile,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar=(Toolbar)findViewById(R.id.actionbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent i2=new Intent(profile.this, dashboard.class);
                        startActivity(i2);
                        return true;
                    case R.id.profile:
                        Intent i1=new Intent(profile.this,profile.class);
                        startActivity(i1);
                        return true;
                    case R.id.lms:
                        Intent i=new Intent(profile.this, webview.class);
                        i.putExtra("url","https://mygyanvihar.com/2020");
                        startActivity(i);
                        return true;
                    default:return false;
                }

            }

        });
        profile=findViewById(R.id.uprofile);
        password=findViewById(R.id.changepassword);
profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(profile.this,user_update.class);
        i.putExtra("position",0);
        startActivity(i);
    }
});
password.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(profile.this,user_update.class);
        i.putExtra("position",1);
        startActivity(i);
    }
});

    }
}