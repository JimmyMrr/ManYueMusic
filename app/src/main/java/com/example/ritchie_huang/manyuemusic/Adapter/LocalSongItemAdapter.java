package com.example.ritchie_huang.manyuemusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.DataItem.MP3Info;
import com.example.ritchie_huang.manyuemusic.R;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class LocalSongItemAdapter extends RecyclerView.Adapter<LocalSongItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<MP3Info> mp3InfoList;

    public LocalSongItemAdapter(Context mContext, List<MP3Info> mp3InfoList) {
        this.mContext = mContext;
        this.mp3InfoList = mp3InfoList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_song_item, null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        MP3Info mp3Info = mp3InfoList.get(position);
        holder.songName.setText(mp3Info.getTitle());
        holder.songArtist.setText(mp3Info.getArtist());

    }

    @Override
    public int getItemCount() {
        return mp3InfoList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView songName;
        private TextView songArtist;
        private ImageView songChoice;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.songName = (TextView) itemView.findViewById(R.id.song_name);
            this.songArtist = (TextView) itemView.findViewById(R.id.song_artist);
            this.songChoice = (ImageView) itemView.findViewById(R.id.song_choice);

            this.songChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //显示歌曲信息
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //播放音乐
        }
    }
}
