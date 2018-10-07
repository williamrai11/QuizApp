package com.example.quizapp.quizss.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.DbDao;
import com.example.quizapp.quizss.data.DbHelper;
import com.example.quizapp.quizss.data.model.QueryOneCsv;
import com.example.quizapp.quizss.features.RecyclerAdapter;
import com.example.quizapp.quizss.features.ResultPage.Result;
import com.example.quizapp.quizss.features.VericalSpacingItemDecoration;


import com.example.quizapp.quizss.features.quizpage.QuizPage;
import com.example.quizapp.quizss.ui.ClickListener;
import com.example.quizapp.quizss.ui.ScoreCard;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ClickListener {

    private RecyclerView recyclerView;
    private String TAG = "csvdatas";
    private List<String> categoriesLists;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");


        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        AdRequest adRequest = new AdRequest.Builder() .build() ;
        adView.loadAd(adRequest);

       /* mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
*/



        preferences = getSharedPreferences("categoryName",0);


        recyclerView = findViewById(R.id.section_recycler_view);
        DbDao dbDao = new DbDao(this);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final String descriptions[] = getResources().getStringArray(R.array.descriptions);
        categoriesLists = dbDao.getAllCategory();

        RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext(), categoriesLists,descriptions);
        adapter.setClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new VericalSpacingItemDecoration(30));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scorecard){
            Intent intent = new Intent(this,ScoreCard.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void itemClicked(int Position, String categoryName) {
        Intent intent = new Intent(this,QuizPage.class);
        //intent.putExtra("categoryId",data.getId());
        //intent.putExtra("categoryName",categoryName);

        editor = preferences.edit();
        editor.putString("categoryName",categoryName);
        editor.apply();

        startActivity(intent);
    }
}
