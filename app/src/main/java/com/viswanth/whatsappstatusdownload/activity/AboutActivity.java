package com.viswanth.whatsappstatusdownload.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.plus.PlusOneButton;
import com.viswanth.whatsappstatusdownload.R;

public class AboutActivity extends AppCompatActivity {

    private PlusOneButton mPlusOneButton;
    private static final int PLUS_ONE_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar =  findViewById(R.id.toolbarabout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);


        Button licenseGlide = findViewById(R.id.licenseinfoglide);
        licenseGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AboutActivity.this,LicenseActivity.class);
                i.putExtra("lib","glide");
                startActivity(i);

            }
        });

        Button licensePhotoview = findViewById(R.id.licenseinfophotoview);
        licensePhotoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(AboutActivity.this,LicenseActivity.class);
                i.putExtra("lib","photoview");
                startActivity(i);

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlusOneButton.initialize("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName(), PLUS_ONE_REQUEST_CODE);


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
