package com.example.ritchie_huang.manyuemusic.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.ritchie_huang.manyuemusic.Adapter.PersonalRecommandAdapter;
import com.example.ritchie_huang.manyuemusic.DataItem.BannerItem;
import com.example.ritchie_huang.manyuemusic.DataItem.Focus;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.BMA;
import com.example.ritchie_huang.manyuemusic.Util.HttpUtil;
import com.example.ritchie_huang.manyuemusic.Util.NetworkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ritchie-huang on 16-8-2.
 */
public class PersonalRecommendFrag extends Fragment {
    private ConvenientBanner<BannerItem> convenientBanner;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PersonalRecommandAdapter adapter;
    private FrameLayout frameLayout;
    private boolean isFromCache;
    private List<BannerItem> list;
    private String[] moduleNames = new String[]{"推荐歌单", "最新音乐", "主播电台"};
    private int[] moduleImageIds = new int[]{R.mipmap.ic_library_music_white_36dp, R.mipmap.ic_album_white_36dp, R.mipmap.ic_radio_white_36dp};
    private List<RecyclerView.Adapter> adapterList;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_personal_recommand, container, false);
        init(view);

        loadAdapter();
        adapter = new PersonalRecommandAdapter(getContext(), moduleNames,moduleImageIds,adapterList);
        recyclerView.setAdapter(adapter);
        return view;
    }



    private void init(View view) {

        adapterList = new ArrayList<>();
        convenientBanner = (ConvenientBanner<BannerItem>) view.findViewById(R.id.convenientBanner);
        recyclerView = (RecyclerView) view.findViewById(R.id.father_recylerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        //顶部广告显示栏
        new BannerAsync().execute();
    }

    public class BannerAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (NetworkUtils.isConnectInternet(getContext())) {
                isFromCache = false;
            }
            list = new ArrayList<>();
            JsonArray array = HttpUtil.getResposeJsonObject(BMA.focusPic(7), getContext(), isFromCache).get("pic").getAsJsonArray();
            Gson gson = new Gson();

            for (int i = 0; i < array.size(); i++) {
                BannerItem bannerItem = new BannerItem();
                Focus focus = gson.fromJson(array.get(i), Focus.class);
                if (focus != null) {
                    bannerItem.setBanner_image_url(focus.getRandpic());
                }
                list.add(bannerItem);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {

                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, list)
                    .setPageIndicator(new int[]{R.mipmap.ic_radio_button_unchecked_white_24dp, R.mipmap.ic_radio_button_checked_white_24dp})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        }
    }


    private class NetworkImageHolderView implements Holder<BannerItem> {

        private View view;

        @Override
        public View createView(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerItem data) {
            ((SimpleDraweeView) view.findViewById(R.id.banner_image)).setImageURI(Uri.parse(data.getBanner_image_url()));
        }
    }


    private void loadAdapter() {
        Gson gson = new Gson();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.execute();






    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }


}
