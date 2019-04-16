package com.music.manyue.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritchie_huang.manyuemusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-10.
 */
public class AllGedanFrag extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private playListAdapter mPlayListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_gedan, container, false);

        mFragmentList = new ArrayList<>();
        AllPlayListFromBMAFrag allPlayListFromBMAFrag = new AllPlayListFromBMAFrag();
        AllPlayListFromNeteaseFrag allPlayListFromNeteaseFrag = new AllPlayListFromNeteaseFrag();

        mFragmentList.add(allPlayListFromBMAFrag);
        mFragmentList.add(allPlayListFromNeteaseFrag);
        initView(view);



        return view;
    }

    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.yellow));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.red));
        mTabLayout.setupWithViewPager(mViewPager);

        mPlayListAdapter = new playListAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPlayListAdapter);
        mViewPager.setCurrentItem(0);
    }


    public class playListAdapter extends FragmentPagerAdapter {
        private FragmentManager mFragmentManager;


        public playListAdapter(FragmentManager fm) {

            super(fm);
            this.mFragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            mFragmentManager.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = mFragmentList.get(position);
            mFragmentManager.beginTransaction().hide(fragment).commit();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0)?"百度云音乐歌单":"网易云音乐歌单";
        }
    }


}
