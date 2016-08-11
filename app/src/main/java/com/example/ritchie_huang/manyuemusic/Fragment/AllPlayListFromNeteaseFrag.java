package com.example.ritchie_huang.manyuemusic.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritchie_huang.manyuemusic.Adapter.AllGedanFromNeteaseListAdapter;
import com.example.ritchie_huang.manyuemusic.DataItem.GedanListNeteaseItem;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.Api;
import com.example.ritchie_huang.manyuemusic.Util.HttpUtil;
import com.example.ritchie_huang.manyuemusic.Util.NetworkUtils;
import com.example.ritchie_huang.manyuemusic.Widget.DividerItemDecoration;
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
    private List<GedanListNeteaseItem> gedanListItemListNetease;
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
                    JsonObject result = HttpUtil.getResposeJsonObject(Api.GEDAN, getContext(), isFromCache);
                    JsonArray array = result.get("playlists").getAsJsonArray();
                    if (array == null) {
                        return null;
                    }
                    for(int i = 0;i < array.size();i++) {
                        GedanListNeteaseItem item = gson.fromJson(array.get(i), GedanListNeteaseItem.class);
                        gedanListItemListNetease.add(item);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter = new AllGedanFromNeteaseListAdapter(getContext(), gedanListItemListNetease);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        }.execute();

    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.father_recylerview);
        gedanListItemListNetease = new ArrayList<>();




    }


}
