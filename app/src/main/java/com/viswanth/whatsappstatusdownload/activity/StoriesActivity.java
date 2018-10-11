package com.viswanth.whatsappstatusdownload.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.viswanth.whatsappstatusdownload.R;
import com.viswanth.whatsappstatusdownload.data.FilesData;
import com.viswanth.whatsappstatusdownload.fragments.ImageFragment;
import com.viswanth.whatsappstatusdownload.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class StoriesActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_activity_saved_stories);




        Toolbar toolbar =  findViewById(R.id.toolbar);

        if(FilesData.getRecentOrSaved().equals("recent")) {
            toolbar.setTitle(getString(R.string.title_activity_recent_stories));
        }else{
            toolbar.setTitle(getString(R.string.title_activity_saved_stories));
        }
        setSupportActionBar(toolbar);

        //back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager =  findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout =  findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        private final List<Fragment>fragments = new ArrayList<>();
        private final List<String>fragmentNames = new ArrayList<>();



        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.clear();
            fragments.add(new ImageFragment());
            fragmentNames.add(getString(R.string.tab_image));
            fragments.add(new VideoFragment());
            fragmentNames.add(getString(R.string.tab_video));

        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames.get(position);
        }
    }
    /**/
}
