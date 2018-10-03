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

public class ImageFragment extends Fragment {



    public ImageFragment(){


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.st_video_image_fragment,container,false);




        if(FilesData.getRecentOrSaved().equals("recent")) {
            RecyclerInstances.recentImageRecyclerview =  v.findViewById(R.id.videoImageRecyclerView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            RecyclerInstances.recentImageRecyclerview.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            RecyclerInstances.recentImageRecyclerview.setLayoutManager(mLayoutManager);

            if(FilesData.getWhatsAppFilesImages().isEmpty()){
                    FilesData.scrapWhatsAppFiles();

            }
            RecyclerInstances.recentImageAdapter = new RecyclerAdapter(FilesData.getWhatsAppFilesImages(), getContext(), 'i');
            RecyclerInstances.recentImageRecyclerview.setAdapter(RecyclerInstances.recentImageAdapter);

        }else{

            RecyclerInstances.savedImageRecyclerview =  v.findViewById(R.id.videoImageRecyclerView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            RecyclerInstances.savedImageRecyclerview.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            RecyclerInstances.savedImageRecyclerview.setLayoutManager(mLayoutManager);


            if(FilesData.getSavedFilesImages().isEmpty()){

                    FilesData.scrapSavedFiles();

            }


            RecyclerInstances.savedImageAdapter = new RecyclerAdapter(FilesData.getSavedFilesImages(), getContext(), 'i');
            RecyclerInstances.savedImageRecyclerview.setAdapter(RecyclerInstances.savedImageAdapter);
        }




        return v;
    }




}







