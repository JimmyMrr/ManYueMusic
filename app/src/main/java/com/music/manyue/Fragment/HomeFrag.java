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

import com.music.manyue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-3.
 */
public class HomeFrag extends Fragment {

    public static List<Fragment> fragmentList;
    public static ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        fragmentList = new ArrayList<>();
        PersonalRecommendFrag personalRecommendFrag = new PersonalRecommendFrag();
        AllSongsFrag allSongsFrag = new AllSongsFrag();
        AllGedanFrag allGedanFrag = new AllGedanFrag();
//        AllPlayListFromNeteaseFrag allPlayListFromNeteaseFrag = new AllPlayListFromNeteaseFrag();
//        AllPlayListFromBMAFrag allPlayListFromBMAFrag = new AllPlayListFromBMAFrag();
        fragmentList.add(personalRecommendFrag);
        fragmentList.add(allSongsFrag);
//        fragmentList.add(allPlayListFromNeteaseFrag);
//        fragmentList.add(allPlayListFromBMAFrag);
        fragmentList.add(allGedanFrag);
        viewPager = (ViewPager) view.findViewById(R.id.container);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        tabLayout.setupWithViewPager(viewPager);


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

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "个性推荐";
                case 1:
                    return "歌曲";
                case 2:
                    return "歌单";
            }
            return null;
        }
    }


}
