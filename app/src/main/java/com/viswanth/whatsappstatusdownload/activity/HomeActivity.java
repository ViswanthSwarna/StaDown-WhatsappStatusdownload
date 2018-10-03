package com.viswanth.whatsappstatusdownload.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.data.FilesData;
import com.viswanth.whatsappstatusdownload.justtheirbecauserequired.StatusDownloaderAnalytics;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private int pressedButtonId = 0;

    //pressedButtonId = 1 for recent stories
    //pressedButtonId = 2 for saved stories

    private Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_activity_home);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(shouldItShowRateUs()){
            showRateUsDialog();

        }


        //ad
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        AdView adViewTop = findViewById(R.id.homeAdviewtop);
        AdView adViewBottom = findViewById(R.id.homeAdviewbottom);
        AdRequest adRequestTop = new AdRequest.Builder().build();
        adViewTop.loadAd(adRequestTop);
        AdRequest adRequestBottom = new AdRequest.Builder().build();
        adViewBottom.loadAd(adRequestBottom);

        //analytics
        StatusDownloaderAnalytics application = (StatusDownloaderAnalytics) getApplication();
        mTracker = application.getDefaultTracker();




    }


    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("HomeActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_action_bar,menu);
         return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.share_actionbar:
                shareApp();
                break;
                }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_about) {

            showAbout();

        } else if (id == R.id.nav_rate_us) {
            askForRating();


        } else if (id == R.id.nav_share) {
            shareApp();
        }else if(id == R.id.nav_help){

            goToHelp();
        }else if(id == R.id.nav_send_mail){
            sendUsMail();

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void onRecentStories(View v){
        pressedButtonId =1;
        requestStoragePermission();

    }


    public void onSavedStories(View v){
        pressedButtonId =2;
        requestStoragePermission();


    }



    public void onHowItWorks(View v){
        goToHelp();
    }



    private void goToRecentStoriesTab(){


            FilesData.scrapWhatsAppFiles();
            FilesData.setRecentOrSaved("recent");
            Intent i = new Intent(HomeActivity.this,StoriesActivity.class);
            startActivity(i);


    }



    private void goToSavedStoriesTab(){


            FilesData.scrapSavedFiles();
            FilesData.setRecentOrSaved("saved");
            Intent i = new Intent(HomeActivity.this,StoriesActivity.class);
            startActivity(i);





    }


    private void goToHelp(){

        Intent i = new Intent(this,HelpActivity.class);
        startActivity(i);

  }


    private void shareApp() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,getString(R.string.share_using)));

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());



    }



    private void requestStoragePermission() {


        if (Build.VERSION.SDK_INT >= 23) {
        final String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 123);
        }else{

            if(pressedButtonId ==1){

                goToRecentStoriesTab();

            }else if(pressedButtonId ==2){

                goToSavedStoriesTab();

            }

        }
        }else{

            if(pressedButtonId ==1){

                goToRecentStoriesTab();

            }else if(pressedButtonId ==2){

                goToSavedStoriesTab();

            }


        }

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @Nullable String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 123){


            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if(pressedButtonId ==1){

                    goToRecentStoriesTab();

                }else if(pressedButtonId ==2){

                    goToSavedStoriesTab();
                }

            }else{

                Toast.makeText(this,getString(R.string.if_not_permitted),Toast.LENGTH_SHORT).show();

            }
        }
    }



    public void askForRating(){

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName())));
        }

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Rate")
                .build());


    }



    public void showAbout(){


        Intent i = new Intent(this,AboutActivity.class);
        startActivity(i);


    }


    public boolean shouldItShowRateUs(){

       SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("itissaved", Context.MODE_PRIVATE);
        int openedTimes = sharedPref.getInt("opennumber",5);
        boolean rated = sharedPref.getBoolean("israted",false);
        openedTimes++;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("opennumber",openedTimes);
        editor.apply();


        return ((!rated && (openedTimes%5) == 0));

    }

    public void showRateUsDialog(){

        final Dialog rateUsDialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.ask_for_rating, null);
        rateUsDialog.setContentView(view);
        rateUsDialog.setCanceledOnTouchOutside(false);
        Window w = rateUsDialog.getWindow();
        if(w != null)
            w.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        Button buttonDismiss = rateUsDialog.findViewById(R.id.buttondismiss);
        final CheckBox cc = rateUsDialog.findViewById(R.id.checkBoxneverask);

        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateUsDialog.dismiss();
                if(cc.isChecked()){
                    neverAskForRatingAgain();

                }

            }
        });

        Button buttonRateNow = rateUsDialog.findViewById(R.id.buttonratenow);
        buttonRateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForRating();
                neverAskForRatingAgain();
            }
        });

        RatingBar rb = rateUsDialog.findViewById(R.id.ratingBar);
        rb.setRating(5);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //askForRating();
            }
        });

        rateUsDialog.show();



    }


     private void sendUsMail(){


         Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                 "mailto", "swarnaappquery@gmail.com", null));
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Status Downloader {your subject}");
         startActivity(Intent.createChooser(emailIntent, null));



     }




     private void neverAskForRatingAgain(){

         SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("itissaved", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putBoolean("israted",true);
         editor.apply();


     }







}
