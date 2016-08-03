package com.example.ritchie_huang.manyuemusic.Activity;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.Fragment.PersonalRecommendFrag;
import com.example.ritchie_huang.manyuemusic.Fragment.SongListFrag;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.PersistentCookieStore;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private PersonalRecommendFrag personalRecommendFrag;
    private SongListFrag songListFrag;
    private FrameLayout frameLayout;
    private List<Fragment> fragmentList;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    public static OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(
                new PersistentCookieStore(getApplicationContext()),
                CookiePolicy.ACCEPT_ALL));


        fragmentList = new ArrayList<>();
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        personalRecommendFrag = new PersonalRecommendFrag();
        songListFrag = new SongListFrag();
        fragmentList.add(personalRecommendFrag);
        fragmentList.add(songListFrag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("漫悦音乐");
        toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(toolbar);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        frameLayout = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.fram,null);
        mViewPager = (ViewPager) frameLayout.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);

//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.searchIcon) {
            return true;

        }
        if (id == R.id.musicIcon) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container,fragmentList.get(1)).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


    }
}
