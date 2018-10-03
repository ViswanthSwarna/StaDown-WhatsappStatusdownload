package com.viswanth.whatsappstatusdownload.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viswanth.whatsappstatusdownload.adapters.RecyclerInstances;
import com.viswanth.whatsappstatusdownload.data.FilesData;
import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.adapters.RecyclerAdapter;


public class VideoFragment extends Fragment {



    public VideoFragment(){



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.st_video_image_fragment,container,false);



        if(FilesData.getRecentOrSaved().equals("recent")){

            RecyclerInstances.recentVideoRecyclerview =  v.findViewById(R.id.videoImageRecyclerView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            RecyclerInstances.recentVideoRecyclerview.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            RecyclerInstances.recentVideoRecyclerview.setLayoutManager(mLayoutManager);


            if(FilesData.getWhatsAppFilesVideos().isEmpty()){
                    FilesData.scrapWhatsAppFiles();

            }


            RecyclerInstances.recentVideoAdapter = new RecyclerAdapter(FilesData.getWhatsAppFilesVideos(),getContext(),'v');
            RecyclerInstances.recentVideoRecyclerview.setAdapter(RecyclerInstances.recentVideoAdapter);

        }
        else{

            RecyclerInstances.savedVideoRecyclerview =  v.findViewById(R.id.videoImageRecyclerView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            RecyclerInstances.savedVideoRecyclerview.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            RecyclerInstances.savedVideoRecyclerview.setLayoutManager(mLayoutManager);


            if(FilesData.getSavedFilesVideos().isEmpty()){
                    FilesData.scrapSavedFiles();

            }


            RecyclerInstances.savedVideoAdapter = new RecyclerAdapter(FilesData.getSavedFilesVideos(),getContext(),'v');
            RecyclerInstances.savedVideoRecyclerview.setAdapter(RecyclerInstances.savedVideoAdapter);

        }




        return v;
    }





}
