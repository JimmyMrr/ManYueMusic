package com.music.manyue.Activity;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;

import com.music.manyue.Fragment.HomeFrag;
import com.music.manyue.Listener.PermissionInterface;
import com.music.manyue.R;
import com.music.manyue.Util.PermissionHelper;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements PermissionInterface {

    private HomeFrag homeFrag;
    private FrameLayout frameLayout;
    public static List<Fragment> fragmentList;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private PermissionHelper mPermissionHelper;

    private ViewPager mViewPager;
//    public static OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);


        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
//        client = new OkHttpClient();
//        client.setCookieHandler(new CookieManager(
//                new PersistentCookieStore(getApplicationContext()),
//                CookiePolicy.ACCEPT_ALL));


    }

    private void initViews() {
        fragmentList = new ArrayList<>();
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        homeFrag = new HomeFrag();
        fragmentList.add(homeFrag);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("漫悦音乐");
        toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(toolbar);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        frameLayout = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.fram, null);
        mViewPager = (ViewPager) frameLayout.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragmentList.get(0)).commit();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            if (fragmentList.get(0).isAdded()) {
//                transaction.hide(fragmentList.get(0)).commit();
//            }
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);


//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, fragmentList.get(1)).commit();

            return true;

        }
        if (id == R.id.musicIcon) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragmentList.get(0).isAdded()) {

                transaction.show(fragmentList.get(0)).commit();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getPermissionsRequestCode() {
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        }
        return new String[0];
    }

    @Override
    public void requestPermissionsSuccess() {
        initViews();
    }

    @Override
    public void requestPermissionsFail() {
        finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
