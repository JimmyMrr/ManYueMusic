package com.music.manyue.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.music.manyue.Adapter.AllGedanFromNeteaseListAdapter;
import com.music.manyue.DataItem.NeteasePlayList;
import com.music.manyue.R;
import com.music.manyue.API.API;
import com.music.manyue.Util.HttpUtil;
import com.music.manyue.Util.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-8.
 */
public class AllPlayListFromNeteaseFrag extends Fragment {

    private boolean isFromCache;
    private RecyclerView recyclerView;
    //    private List<GedanListNeteaseItem> gedanListItemListNetease;
    private List<NeteasePlayList.PlaylistsBean> playlistsBeanList;
    private AllGedanFromNeteaseListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);

        initView(view);
        loadData();


        return view;
    }

    private void loadData() {
        final Gson gson = new Gson();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (NetworkUtils.isConnectInternet(getContext())) {
                    isFromCache = false;
                }

                try {
                    JsonObject result = HttpUtil.getResposeJsonObject(API.GEDAN, getContext(), isFromCache);
                    JsonArray array = result.get("playlists").getAsJsonArray();
                    if (array == null) {
                        return null;
                    }
                    for(int i = 0;i < array.size();i++) {
                        NeteasePlayList.PlaylistsBean bean = gson.fromJson(array.get(i), NeteasePlayList.PlaylistsBean.class);
                        playlistsBeanList.add(bean);
//                        GedanListNeteaseItem item = gson.fromJson(array.get(i), GedanListNeteaseItem.class);
//                        gedanListItemListNetease.add(item);

                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                adapter = new AllGedanFromNeteaseListAdapter(getContext(), gedanListItemListNetease);
                adapter = new AllGedanFromNeteaseListAdapter(getContext(), playlistsBeanList);

                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        }.execute();

    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.father_recylerview);
//        gedanListItemListNetease = new ArrayList<>();
        playlistsBeanList = new ArrayList<>();




    }


}
