package com.example.quizapp.quizss.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.local.providers.DbDao;
import com.example.quizapp.quizss.features.RecyclerAdapter;
import com.example.quizapp.quizss.features.VericalSpacingItemDecoration;


import com.example.quizapp.quizss.features.quizpage.TestQuizPage;
import com.example.quizapp.quizss.util.LogUtil;
import com.example.quizapp.quizss.util.ToastUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ClickListener {

    private RecyclerView recyclerView;
    private String TAG = "csvdatas";
    private List<String> categoriesLists;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private DbDao dbDao;


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
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

       /* mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
*/
        preferences = getSharedPreferences("categoryName", 0);


        recyclerView = findViewById(R.id.section_recycler_view);
        dbDao = new DbDao(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final String descriptions[] = getResources().getStringArray(R.array.descriptions);
        categoriesLists = dbDao.getAllCategory();

        for (String names : categoriesLists) {
            LogUtil.dLog("test", names);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext(), categoriesLists, descriptions);
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

        if (id == R.id.nav_scorecard) {
            Intent intent = new Intent(this, ResultTwo.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void itemClicked(int Position, String categoryName) {
        //intent.putExtra("categoryId",data.getId());
        //intent.putExtra("categoryName",categoryName);

        createDialog(categoryName);

        editor = preferences.edit();
        editor.putString("categoryName", categoryName);
        editor.apply();


    }


    AlertDialog dialog;

    //Dialog for choosing difficulty
    private void createDialog(String categoryName) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.difficulty_choose_layout, null);

        Button easy = view.findViewById(R.id.easy);
        Button medium = view.findViewById(R.id.medium);
        Button hard = view.findViewById(R.id.hard);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout((6 * width) / 7, (3 * height) / 6);

        easy.setOnClickListener((View v) -> {
            runQuiz(categoryName,"easy");
        });

        medium.setOnClickListener((View v) -> {
            runQuiz(categoryName,"medium");
        });

        hard.setOnClickListener((View v) -> {
            runQuiz(categoryName,"hard");
        });

    }

    private void runQuiz(String categoryName,String difficulty) {
        //if (dbDao.checkForQuestions(categoryName,difficulty)) {

            Intent intent = new Intent(this, TestQuizPage.class);
            intent.putExtra("difficulty", difficulty);
            dialog.dismiss();
            startActivity(intent);

            /* }else {
            ToastUtil.shortToast(getApplicationContext(),"Hello");
        }*/

    }

}
