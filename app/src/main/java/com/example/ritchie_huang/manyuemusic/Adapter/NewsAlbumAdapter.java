package com.example.ritchie_huang.manyuemusic.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.DataItem.NewsAlbumItem;
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
public class NewsAlbumAdapter extends RecyclerView.Adapter<NewsAlbumAdapter.ViewHolder> {
    int width = 160,height = 160;

    private Context mContext;
    private List<NewsAlbumItem> list;

    public NewsAlbumAdapter(Context mContext, List<NewsAlbumItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void update(ArrayList<NewsAlbumItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommend_newalbums_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(NewsAlbumAdapter.ViewHolder holder, int position) {
        NewsAlbumItem item = list.get(position);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(item.coverImgUrl))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(holder.album_art.getController())
                .setImageRequest(request)
                .build();
        holder.album_art.setController(controller);
        holder.artist_name.setText(item.artistName);
        holder.album_name.setText(item.albumName);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView album_art;
        TextView album_name;
        TextView artist_name;
        public ViewHolder(View itemView) {
            super(itemView);
            this.album_art = (SimpleDraweeView) itemView.findViewById(R.id.album_art);
            this.artist_name = (TextView) itemView.findViewById(R.id.artist_name);
            this.album_name = (TextView) itemView.findViewById(R.id.album_name);
        }
    }
}
