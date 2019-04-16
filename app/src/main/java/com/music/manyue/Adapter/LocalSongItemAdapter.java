package com.music.manyue.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.music.manyue.DataItem.MP3InfoItem;
import com.music.manyue.R;
import com.music.manyue.ViewHolder.CommonItemViewHolder;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class LocalSongItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MP3InfoItem> mp3InfoItemList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private static final int COMMON_TYPE = 0;
    private static final int ITEM_TYPE = 1;

    public LocalSongItemAdapter(Context mContext, List<MP3InfoItem> mp3InfoItemList) {
        this.mContext = mContext;
        this.mp3InfoItemList = mp3InfoItemList;
    }


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMMON_TYPE) {
            return new CommonItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.common_item, null));
        } else {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_playlist_detail_item, null));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            MP3InfoItem mp3InfoItem = mp3InfoItemList.get(position-1);
            ((ItemViewHolder) holder).songName.setText(mp3InfoItem.getTitle());
            ((ItemViewHolder) holder).songArtist.setText(mp3InfoItem.getArtist());
            ((ItemViewHolder) holder).songTrack.setText(position + "");

            if (mOnItemClickListener != null) {
                ((ItemViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, position-1);
                    }
                });

                ((ItemViewHolder) holder).songChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } else if (holder instanceof CommonItemViewHolder) {
            ((CommonItemViewHolder) holder).textView.setText("(共" + mp3InfoItemList.size() + "首)");

            ((CommonItemViewHolder) holder).select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        return (position == COMMON_TYPE) ? COMMON_TYPE : ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return (null != mp3InfoItemList ? mp3InfoItemList.size() + 1 : 0);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView songTrack;
        private TextView songName;
        private TextView songArtist;
        private ImageView songChoice;
        public View rootView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            this.songName = (TextView) itemView.findViewById(R.id.song_title);
            this.songArtist = (TextView) itemView.findViewById(R.id.song_artist);
            this.songChoice = (ImageView) itemView.findViewById(R.id.popup_menu);
            this.songTrack = (TextView) itemView.findViewById(R.id.trackNumber);


        }

    }

}
