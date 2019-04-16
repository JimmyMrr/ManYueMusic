package com.music.manyue.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.music.manyue.Adapter.HotGedanListAdapter;
import com.music.manyue.Adapter.HotNewsAlbumAdapter;
import com.music.manyue.Listener.OnRecyclerItemClickListener;
import com.music.manyue.Adapter.PersonalRecommandAdapter;
import com.music.manyue.Adapter.HotRadioAdapter;
import com.music.manyue.DataItem.BannerItem;
import com.music.manyue.DataItem.Focus;
import com.music.manyue.DataItem.GedanHotItem;
import com.music.manyue.DataItem.NewsAlbumItem;
import com.music.manyue.DataItem.RadioItem;
import com.music.manyue.R;
import com.music.manyue.Util.BMA;
import com.music.manyue.Util.HttpUtil;
import com.music.manyue.Util.NetworkUtils;
import com.music.manyue.Widget.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private String[] moduleNames = new String[]{"推荐歌单", "最新专辑", "主播电台"};
    private int[] moduleImageIds = new int[]{R.mipmap.ic_library_music_white_36dp, R.mipmap.ic_album_white_36dp, R.mipmap.ic_radio_white_36dp};
    private List<RecyclerView.Adapter> adapterList;
    private List<GedanHotItem> gedanHotItemList;
    private List<NewsAlbumItem> newsAlbumItemList;
    private List<RadioItem> radioItemList;
    private HotGedanListAdapter hotGedanListAdapter;
    private HotNewsAlbumAdapter hotNewsAlbumAdapter;
    private HotRadioAdapter hotRadioAdapter;

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

        return view;
    }


    private void init(View view) {
        gedanHotItemList = new ArrayList<>();
        newsAlbumItemList = new ArrayList<>();
        radioItemList = new ArrayList<>();

        convenientBanner = (ConvenientBanner<BannerItem>) view.findViewById(R.id.convenientBanner);
        recyclerView = (RecyclerView) view.findViewById(R.id.father_recylerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
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
            JsonArray array = null;
            try {
                array = HttpUtil.getResposeJsonObject(BMA.focusPic(7), getContext(), isFromCache).get("pic").getAsJsonArray();
                Gson gson = new Gson();

                for (int i = 0; i < array.size(); i++) {
                    BannerItem bannerItem = new BannerItem();
                    Focus focus = gson.fromJson(array.get(i), Focus.class);
                    if (focus != null) {
                        bannerItem.setBanner_image_url(focus.getRandpic());
                    }
                    list.add(bannerItem);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
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
        final Gson gson = new Gson();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (NetworkUtils.isConnectInternet(getContext())) {
                    isFromCache = false;
                }
                String fmtrash = "http://music.163.com/api/radio/get";
                String newAlbums = "http://music.163.com/api/album/new?area=ALL&offset=" + "0" + "&total=true&limit=" + "6";

                //最热歌单
                try {
                    JsonObject result = HttpUtil.getResposeJsonObject(BMA.GeDan.hotGeDan(6), getContext(), isFromCache);
                    if (result == null) {
                        return null;
                    }
                    JsonArray array = result.get("content").getAsJsonObject().get("list").getAsJsonArray();
                    if (array == null) {
                        return null;
                    }
                    for (int i = 0; i < array.size(); i++) {
                        GedanHotItem gedanHotItem = gson.fromJson(array.get(i), GedanHotItem.class);
                        gedanHotItemList.add(gedanHotItem);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                //最新专辑
                try {
                    JsonObject result1 = HttpUtil.getResposeJsonObject(newAlbums, getContext(), isFromCache);
                    JsonArray array1 = result1.get("albums").getAsJsonArray();
                    Iterator it2 = array1.iterator();
                    while (it2.hasNext()) {
                        JsonElement e = (JsonElement) it2.next();
                        JsonObject jo = e.getAsJsonObject();

                        String artistName = jo.get("artist").getAsJsonObject().get("name").getAsString();
                        NewsAlbumItem newsAlbumItem = new NewsAlbumItem(getStringValue(jo, "blurPicUrl"), getIntValue(jo, "id"),
                                getStringValue(jo, "name"), artistName, getIntValue(jo, "publishTime"));
                        newsAlbumItemList.add(newsAlbumItem);
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


                //最热Radio
                try {
                    JsonObject result2 = HttpUtil.getResposeJsonObject(BMA.Radio.recommendRadioList(6), getContext(), isFromCache);
                    JsonArray array2 = result2.get("list").getAsJsonArray();
                    for (int i = 0; i < array2.size(); i++) {
                        RadioItem radioItem = gson.fromJson(array2.get(i), RadioItem.class);
                        radioItemList.add(radioItem);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                adapterList = new ArrayList<RecyclerView.Adapter>();
                hotGedanListAdapter = new HotGedanListAdapter(getContext(), gedanHotItemList);
                hotNewsAlbumAdapter = new HotNewsAlbumAdapter(getContext(), newsAlbumItemList);
                hotRadioAdapter = new HotRadioAdapter(getContext(), radioItemList);
                adapterList.add(hotGedanListAdapter);
                adapterList.add(hotNewsAlbumAdapter);
                adapterList.add(hotRadioAdapter);

                adapter = new PersonalRecommandAdapter(getContext(), moduleNames, moduleImageIds, adapterList);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0) {
                            //点击更多设置跳转到对应界面
                            HomeFrag.viewPager.setCurrentItem(2);

                        } else {
                            view.setVisibility(View.GONE);
//                            Intent intent = new Intent(getContext(), PlayingActivity.class);
//                            startActivity(intent);
                        }
//                        if (position == 2) {
//                            Intent intent = new Intent(getContext(), PlayingActivity.class);
//                            startActivity(intent);
//
//                        }
                    }
                });
            }
        }.execute();


    }

    private String getStringValue(JsonObject jsonObject, String key) {
        JsonElement nameElement = jsonObject.get(key);
        return nameElement.getAsString();
    }


    private int getIntValue(JsonObject jsonObject, String key) {
        JsonElement nameElement = jsonObject.get(key);
        return nameElement.getAsInt();
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
