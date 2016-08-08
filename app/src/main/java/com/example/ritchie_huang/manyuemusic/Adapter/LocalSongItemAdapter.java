package com.example.ritchie_huang.manyuemusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritchie_huang.manyuemusic.DataItem.MP3InfoItem;
import com.example.ritchie_huang.manyuemusic.R;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class LocalSongItemAdapter extends RecyclerView.Adapter<LocalSongItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<MP3InfoItem> mp3InfoItemList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public LocalSongItemAdapter(Context mContext, List<MP3InfoItem> mp3InfoItemList) {
        this.mContext = mContext;
        this.mp3InfoItemList = mp3InfoItemList;
    }


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,int position);
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_song_item, null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        MP3InfoItem mp3InfoItem = mp3InfoItemList.get(position);
        holder.songName.setText(mp3InfoItem.getTitle());
        holder.songArtist.setText(mp3InfoItem.getArtist());

        if (mOnItemClickListener != null) {
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });

            holder.songChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mp3InfoItemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView songName;
        private TextView songArtist;
        private ImageView songChoice;
        public View rootView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            this.songName = (TextView) itemView.findViewById(R.id.song_name);
            this.songArtist = (TextView) itemView.findViewById(R.id.song_artist);
            this.songChoice = (ImageView) itemView.findViewById(R.id.song_choice);



        }

    }
}
