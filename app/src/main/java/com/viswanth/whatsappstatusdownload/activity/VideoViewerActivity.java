package com.viswanth.whatsappstatusdownload.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.data.FilesData;
import com.viswanth.whatsappstatusdownload.justtheirbecauserequired.StatusDownloaderAnalytics;

import java.io.File;
import java.util.ArrayList;

public class VideoViewerActivity extends AppCompatActivity {

    int position;
    private static ArrayList<File> videos;
    private Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_viewer);

        if(FilesData.getRecentOrSaved().equals("recent")){

          videos = FilesData.getWhatsAppFilesVideos();

        }else{

           videos =  FilesData.getSavedFilesVideos();
        }

        try{
            position = getIntent().getExtras().getInt("position");
        }catch (NullPointerException e){
            Toast.makeText(this,getString(R.string.imageviewer_error),Toast.LENGTH_SHORT).show();
        }


      final  VideoView vv = findViewById(R.id.videoView);

        vv.setVideoPath(videos.get(position).getPath());
        MediaController mc = new MediaController(this,false);
        vv.setMediaController(mc);
        vv.setFitsSystemWindows(true);
        mc.show(0);
        mc.setVisibility(View.VISIBLE);
        mc.setAnchorView(vv);
        vv.start();

        mc.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // next button clicked
                if((videos.size()-1) == position){
                    position = 0;
                    vv.setVideoPath(videos.get(position).getPath());
                    vv.start();

                }else{
                vv.setVideoPath(videos.get(++position).getPath());
                vv.start();
                }

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(position == 0){
                    position = videos.size()-1;
                    vv.setVideoPath(videos.get(position).getPath());
                    vv.start();
                }else{

                    vv.setVideoPath(videos.get(--position).getPath());
                    vv.start();

                }

            }
        } );

        //analytics
        StatusDownloaderAnalytics application = (StatusDownloaderAnalytics) getApplication();
        mTracker = application.getDefaultTracker();

    }


    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("VideoViewActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


}
