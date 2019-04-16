package com.music.manyue.Fragment;

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

import com.music.manyue.Activity.PlayingActivity;
import com.music.manyue.Adapter.LocalSongItemAdapter;
import com.music.manyue.DataItem.MP3InfoItem;
import com.music.manyue.R;
import com.music.manyue.Service.PlayService;
import com.music.manyue.Util.LocalSongs;
import com.music.manyue.Widget.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class AllSongsFrag extends Fragment {

    //HomeActivity的控件
    private SimpleDraweeView song_image;
    private TextView song_name;
    private TextView song_artist;
    private ImageView list_play;
    private ImageView pause_play;
    private ImageView next_play;


    private RecyclerView recyclerView;
    private LocalSongItemAdapter adapter;
    private List<MP3InfoItem> mp3InfoItemList;
    private LocalSongs localSongs;
    private boolean isPlaying = false;


    private int repeatState;        //循环标识
    private final int isCurrentRepeat = 1; // 单曲循环
    private final int isAllRepeat = 2; // 全部循环
    private final int isNoneRepeat = 3; // 无重复播放

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
        mp3InfoItemList = localSongs.getMp3Infos(getActivity().getContentResolver());
        adapter = new LocalSongItemAdapter(getContext(), mp3InfoItemList);
        adapter.setOnItemClickListener(new LocalSongItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//播放
                MP3InfoItem mp3InfoItem = mp3InfoItemList.get(position);
                Intent intent1 = new Intent(getContext(), PlayingActivity.class);
                Intent intent = new Intent(getContext(), PlayService.class);
                intent.putExtra("url", mp3InfoItem.getUrl());
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
        song_image = (SimpleDraweeView) getActivity().findViewById(R.id.song_img);
        song_artist = (TextView) getActivity().findViewById(R.id.song_artist);
        song_name = (TextView) getActivity().findViewById(R.id.song_name);
        list_play = (ImageView) getActivity().findViewById(R.id.previous_play);
        next_play = (ImageView) getActivity().findViewById(R.id.next_play);
        pause_play = (ImageView) getActivity().findViewById(R.id.pause_play);

    }
}
