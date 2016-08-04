package com.example.ritchie_huang.manyuemusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.Activity.PlayingActivity;
import com.example.ritchie_huang.manyuemusic.Adapter.LocalSongItemAdapter;
import com.example.ritchie_huang.manyuemusic.DataItem.MP3Info;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Service.PlayService;
import com.example.ritchie_huang.manyuemusic.Util.Constants;
import com.example.ritchie_huang.manyuemusic.Util.LocalSongs;
import com.example.ritchie_huang.manyuemusic.Widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class SongsFrag extends Fragment {

    //HomeActivity的控件
    private ImageView song_image;
    private TextView song_name;
    private TextView song_artist;
    private ImageView list_play;
    private ImageView pause_play;
    private ImageView next_play;


    private RecyclerView recyclerView;
    private LocalSongItemAdapter adapter;
    private List<MP3Info> mp3InfoList;
    private LocalSongs localSongs;
    private boolean isPlaying = false;

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
        adapter.setOnItemClickListener(new LocalSongItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//播放
                MP3Info mp3Info = mp3InfoList.get(position);
                Intent intent1 = new Intent(getContext(), PlayingActivity.class);
                Intent intent = new Intent(getContext(), PlayService.class);
                intent.putExtra("url", mp3Info.getUrl());
                intent.putExtra("pos", position);
                isPlaying = true;
                startActivity(intent1);
            }

        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.song_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);

        //初始化HomeActivity中的控件
        song_image = (ImageView) getActivity().findViewById(R.id.song_img);
        song_artist = (TextView) getActivity().findViewById(R.id.song_artist);
        song_name = (TextView) getActivity().findViewById(R.id.song_name);
        list_play = (ImageView) getActivity().findViewById(R.id.list_play);
        next_play = (ImageView) getActivity().findViewById(R.id.next_play);
        pause_play = (ImageView) getActivity().findViewById(R.id.pause_play);

    }
}
