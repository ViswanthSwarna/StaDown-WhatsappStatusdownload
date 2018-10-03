package com.viswanth.whatsappstatusdownload.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.justtheirbecauserequired.StatusDownloaderAnalytics;

public class HelpActivity extends AppCompatActivity {
    private Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar =  findViewById(R.id.toolbarhowitworks);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AdView adViewTop = findViewById(R.id.adViewhelp);
        AdRequest adRequestHelp = new AdRequest.Builder().build();
        adViewTop.loadAd(adRequestHelp);



        //analytics
        StatusDownloaderAnalytics application = (StatusDownloaderAnalytics) getApplication();
        mTracker = application.getDefaultTracker();

        Button buttonmail = findViewById(R.id.buttonmail);
        buttonmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToMail();
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("HelpActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void sendToMail(){


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "swarnaappquery@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Status Downloader {your subject}");
        startActivity(Intent.createChooser(emailIntent, null));



    }

}
