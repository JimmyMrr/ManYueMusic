package com.example.ritchie_huang.manyuemusic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritchie_huang.manyuemusic.Adapter.LocalSongItemAdapter;
import com.example.ritchie_huang.manyuemusic.DataItem.MP3Info;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.LocalSongs;
import com.example.ritchie_huang.manyuemusic.Widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class SongsFrag extends Fragment {

    private RecyclerView recyclerView;
    private LocalSongItemAdapter adapter;
    private List<MP3Info> mp3InfoList;
    private LocalSongs localSongs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_songlist, null, false);
        init(view);
        localSongs = new LocalSongs();
        mp3InfoList = localSongs.getMp3Infos(getActivity().getContentResolver());
        adapter = new LocalSongItemAdapter(getContext(), mp3InfoList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.song_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
