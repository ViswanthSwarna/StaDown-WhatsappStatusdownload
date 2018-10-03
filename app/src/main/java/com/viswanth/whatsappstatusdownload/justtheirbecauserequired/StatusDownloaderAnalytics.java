package com.viswanth.whatsappstatusdownload.justtheirbecauserequired;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.viswanth.whatsappstatusdownload.R;

import static com.google.android.gms.analytics.GoogleAnalytics.*;

public class StatusDownloaderAnalytics extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = getInstance(this);
    }


    synchronized public Tracker getDefaultTracker() {
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
