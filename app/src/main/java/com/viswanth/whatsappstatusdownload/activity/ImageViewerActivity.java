package com.viswanth.whatsappstatusdownload.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.viswanth.whatsappstatusdownload.R;

public class ImageViewerActivity extends AppCompatActivity {

    String path;
    PhotoView mPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_activity_image_viewer);


        Toolbar toolbar = findViewById(R.id.toolimageviewer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        try{
        path = getIntent().getExtras().getString("path");
        }catch (NullPointerException e){
            Toast.makeText(this,getString(R.string.imageviewer_error),Toast.LENGTH_SHORT).show();
        }

        mPhotoView = findViewById(R.id.imageviewer);
        mPhotoView.setImageURI(Uri.parse(path));




    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
