package com.music.manyue.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.music.manyue.Activity.HotGedanDetailActivity;
import com.music.manyue.Activity.HotRadioDetailActivity;
import com.music.manyue.DataItem.RadioItem;
import com.example.ritchie_huang.manyuemusic.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-8.
 */
public class HotRadioAdapter extends RecyclerView.Adapter<HotRadioAdapter.ViewHolder> {
    private Context mContext;
    private List<RadioItem> list;
    int width = 160,height = 160;


    public HotRadioAdapter(Context mContext, List<RadioItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommend_newalbums_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void update(ArrayList<RadioItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RadioItem item = list.get(position);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(item.getPic()))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(holder.album_artist.getController())
                .setImageRequest(request)
                .build();
        holder.album_artist.setController(controller);
        holder.album_name.setText(item.getTitle());
        holder.artist_name.setText(item.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HotRadioDetailActivity.class);
                intent.putExtra("albumid",item.getAlbum_id());
                intent.putExtra("albumart",item.getPic());
                intent.putExtra("albumname", item.getTitle());
                intent.putExtra("artistname", item.getDesc());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView album_artist;
        TextView album_name,artist_name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.album_artist = (SimpleDraweeView) itemView.findViewById(R.id.album_art);
            this.album_name = (TextView) itemView.findViewById(R.id.album_name);
            this.artist_name = (TextView) itemView.findViewById(R.id.artist_name);
        }
    }
}
