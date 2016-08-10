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

import com.example.ritchie_huang.manyuemusic.Adapter.AllGedanFromBMAAdapter;
import com.example.ritchie_huang.manyuemusic.DataItem.GedanBMAItem;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.BMA;
import com.example.ritchie_huang.manyuemusic.Util.HttpUtil;
import com.example.ritchie_huang.manyuemusic.Widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-8.
 */
public class AllPlayListFromBMAFrag extends Fragment {

    private GridLayoutManager gridLayoutManager;
    private AllGedanFromBMAAdapter mAdapter;
    private RecyclerView recyclerView;
    private int lastVisibleItem;
    private List<GedanBMAItem> mList;
    int pageCount = 1;
    Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.father_recylerview);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    new MAsyncTask(++pageCount).execute();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }

        });

        loadData();


    }


    class MAsyncTask extends AsyncTask {

        private int next;

        public MAsyncTask(int next) {
            this.next = next;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            JsonObject result = HttpUtil.getResposeJsonObject(BMA.GeDan.geDan(next, 10));
            if (result == null) {
                return null;
            }
            //热门歌单
            JsonArray pArray = result.get("content").getAsJsonArray();
            if (pArray == null) {
                return null;
            }

            int plen = pArray.size();

            for (int i = 0; i < plen; i++) {
                GedanBMAItem gedan = gson.fromJson(pArray.get(i), GedanBMAItem.class);
                mList.add(gedan);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mAdapter.update(mList);
        }

    }

    private void loadData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                gson = new Gson();
                JsonObject result = HttpUtil.getResposeJsonObject(BMA.GeDan.geDan(1, 10));
                if (result == null) {
                    return null;
                }
                //热门歌单
                JsonArray pArray = result.get("content").getAsJsonArray();
                if (pArray == null) {
                    return null;
                }

                int plen = pArray.size();

                for (int i = 0; i < plen; i++) {
                    GedanBMAItem item = gson.fromJson(pArray.get(i), GedanBMAItem.class);
                    mList.add(item);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapter = new AllGedanFromBMAAdapter(getContext(), mList);
                recyclerView.setAdapter(mAdapter);

            }
        }.execute();

    }


}
