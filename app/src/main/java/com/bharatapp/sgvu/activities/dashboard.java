package com.bharatapp.sgvu.activities;



import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bharatapp.sgvu.R;
import com.bharatapp.sgvu.adapter.SliderAdapter;
import com.bharatapp.sgvu.fragments.admin_link;
import com.bharatapp.sgvu.model_class.SliderData;
import com.bharatapp.sgvu.fragments.important_link;
import com.bharatapp.sgvu.fragments.updates;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
View v,actionbar2;
ImageView close,poster,actionimage;
Toolbar toolbar;
TextView maintitle;
BottomNavigationView bottomNavigationView;
    public DrawerLayout drawerLayout;
    NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ViewPager viewPager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    String url1 = "https://seekho.live/bharat-sir/slider/h3.jpg";
    String url2 = "https://seekho.live/bharat-sir/slider/h2.png";
    String url3 = "https://seekho.live/bharat-sir/slider/h1.jpg";
    String url4 = "https://seekho.live/bharat-sir/slider/h4.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        maintitle=(TextView)findViewById(R.id.maintitle);
        //viewpager
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url4));
        viewPager = (ViewPager)findViewById(R.id.viewpager1);
        SliderAdapter adapter = new SliderAdapter(dashboard.this,sliderDataArrayList);
        viewPager.setAdapter(adapter);
        /*After setting the adapter use the timer */
        int NUM_PAGES=sliderDataArrayList.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        //custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        close=(ImageView)dialogView.findViewById(R.id.clo);
        poster=(ImageView)dialogView.findViewById(R.id.pos);
        toolbar=findViewById(R.id.actionbar1);
        setSupportActionBar(toolbar);
        //navigation drawer
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.navi_blue));
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//load notice fragment
        loadfragment(new updates());
        actionimage=findViewById(R.id.aq);
        actionimage.setImageResource(R.drawable.gyanviharnewlogo);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(dashboard.this, detail_notice.class);
                startActivity(i);
            }
        });
        //bottem navigation
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent i2=new Intent(dashboard.this,dashboard.class);
                        startActivity(i2);
                        return true;
                    case R.id.profile:
                        Intent i1=new Intent(dashboard.this, profile.class);
                        startActivity(i1);
                        return true;
                    case R.id.lms:
                       Intent i=new Intent(dashboard.this, webview.class);
                       i.putExtra("url","https://mygyanvihar.com/2020");
                       startActivity(i);
                        return true;
                    default:return false;
                }

            }

        });



    }


    private void loadfragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.updates,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.up:
                loadfragment(new updates());
                maintitle.setText("IMPORTANT UPDATES");
                break;
            case R.id.links:
                loadfragment(new important_link());
                maintitle.setText("IMPORTANT LINKS");
                break;
            case R.id.admin:
                loadfragment(new admin_link());
                maintitle.setText("ADMIN PANEL");
                break;
            case R.id.shareapp:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            case R.id.logout:
                Toast.makeText(dashboard.this,"Log Out",Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}