package com.viswanth.whatsappstatusdownload.filesoperations;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.data.FilesData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public  class FileOperations {



    public static void deleteAndRefreshFiles(File file) {

        file.delete();
        FilesData.scrapSavedFiles();

    }



    public static void saveAndRefreshFiles(File sourceFile) throws IOException {

        File destFile = new File(Environment.getExternalStorageDirectory().toString()+ FilesData.getSavedFilesLocation(),sourceFile.getName());

        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }

        FilesData.scrapSavedFiles();

    }



    public static  void shareFile(File file, Context c,char type){


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.parse(file.getPath());

        if(type == 'i') {

            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            c.startActivity(Intent.createChooser(shareIntent, c.getString(R.string.share_using)));

        }else{

            shareIntent.setType("video/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            c.startActivity(Intent.createChooser(shareIntent,  c.getString(R.string.share_using)));
        }

    }


}
