package com.viswanth.whatsappstatusdownload.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.activity.ImageViewerActivity;
import com.viswanth.whatsappstatusdownload.activity.VideoViewerActivity;
import com.viswanth.whatsappstatusdownload.data.FilesData;
import com.viswanth.whatsappstatusdownload.filesoperations.FileOperations;
import com.viswanth.whatsappstatusdownload.justtheirbecauserequired.GlideApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


     private ArrayList<File>mDataset =  new ArrayList<>();
     private Context mContext;
     private char contentType;
     private final static int AD_TYPE = 143,CONTENT_TYPE=123;




    public RecyclerAdapter(ArrayList<File> myDataset, Context c, char k) {

        mDataset = myDataset;
        mContext =c;
        contentType = k;

    }



    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {


        CardView v;



        if( mDataset != null && !mDataset.isEmpty()){

            if(viewType == CONTENT_TYPE) {


                if (contentType == 'i') {
                    v = (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.st_recyclerview_image_item, parent, false);


                } else {

                    v = (CardView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.st_recyclerview_video_item, parent, false);


                }


            }else{

                v = (CardView)LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.native_ad_item,parent,false);


            }
        }else{

            v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.st_empty_warning, parent, false);

        }


        return new ViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(mDataset != null  ){


        if(!mDataset.isEmpty()) {

            if(getItemViewType(position) == CONTENT_TYPE){

                final int ourPosition = (position)-((position +1)/3);

            if (contentType == 'i') {

                GlideApp.with(mContext)
                        .load(mDataset.get(ourPosition).getPath())
                        .override(600, 400)
                        .centerCrop()
                        .into(holder.mImageView);

                holder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(mContext, ImageViewerActivity.class);
                        i.putExtra("path",mDataset.get(ourPosition).getPath());
                        mContext.startActivity(i);

                    }
                });


            } else {



               GlideApp.with(mContext)
                        .load(mDataset.get(ourPosition).getPath())
                        .override(600, 400)
                        .centerCrop()
                        .into(holder.mImageView);


                holder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        Intent i = new Intent(mContext, VideoViewerActivity.class);
                        i.putExtra("position",ourPosition);
                        mContext.startActivity(i);


                    }
                });



            }



            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    FileOperations.shareFile(mDataset.get(ourPosition), mContext,contentType);


                }
            });


            if(!FilesData.getRecentOrSaved().equals("recent")){

                holder.saveOrDelete.setText(mContext.getString(R.string.delete));

            }
            holder.saveOrDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (FilesData.getRecentOrSaved().equals("recent")) {

                        try {

                            FileOperations.saveAndRefreshFiles(mDataset.get(ourPosition));
                            Toast.makeText(mContext, mContext.getString(R.string.story_saved), Toast.LENGTH_SHORT).show();


                        } catch (IOException e) {

                            Toast.makeText(mContext, mContext.getString(R.string.saving_failed), Toast.LENGTH_SHORT).show();


                        }


                    } else {



                            if (contentType == 'i') {


                                FileOperations.deleteAndRefreshFiles(FilesData.getSavedFilesImages().get(ourPosition));
                                mDataset = FilesData.getSavedFilesImages();


                                RecyclerInstances.savedImageAdapter.notifyDataSetChanged();
                                RecyclerInstances.savedImageRecyclerview.setAdapter(RecyclerInstances.savedImageAdapter);





                            } else {

                                FileOperations.deleteAndRefreshFiles(FilesData.getSavedFilesVideos().get(ourPosition));
                                mDataset = FilesData.getSavedFilesVideos();

                                RecyclerInstances.savedVideoAdapter.notifyDataSetChanged();
                                RecyclerInstances.savedVideoRecyclerview.setAdapter(RecyclerInstances.savedVideoAdapter);




                            }



                    }
                }
            });



            }else{

                AdRequest adRequest = new AdRequest.Builder().build();
                holder.adView.loadAd(adRequest);



            }

        }
        }
    }


    @Override
    public int getItemViewType(int position)
    {
        if ((position+1) % 3 == 0){
            return AD_TYPE;}
        else {
            return CONTENT_TYPE;
        }}


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if(mDataset != null){
        if(mDataset.isEmpty()){

            return 1;
        }else {
        return (mDataset.size()+(mDataset.size()/2));
        }
        }else{
        return 0;}
    }










    //viewHolder InnerClass

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private Button shareButton, saveOrDelete;
        private AdView adView;



        public ViewHolder(CardView v) {
            super(v);

            mImageView =  v.findViewById(R.id.vvr);
            shareButton =  v.findViewById(R.id.sharefilebutton);
            saveOrDelete =  v.findViewById(R.id.saveORdelete);
            adView =  v.findViewById(R.id.adviewlist);

        }



    }




}






