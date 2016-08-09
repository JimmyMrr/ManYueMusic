package com.example.ritchie_huang.manyuemusic.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.DataItem.GedanListNeteaseItem;
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
 * Created by ritchie-huang on 16-8-6.
 */
public class AllGedanFromNeteaseListAdapter extends RecyclerView.Adapter<AllGedanFromNeteaseListAdapter.ItemViewHolder> {
    private Context mContext;
    private List<GedanListNeteaseItem> gedanListItemListNetease;
    SpannableString spanString;

    int width = 140,height = 140;


    public AllGedanFromNeteaseListAdapter(Context mContext, List<GedanListNeteaseItem> gedanListItemListNetease) {
        this.mContext = mContext;
        this.gedanListItemListNetease = gedanListItemListNetease;

        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.index_icn_earphone);
        ImageSpan imgSpan = new ImageSpan(mContext, b, ImageSpan.ALIGN_BASELINE);
        spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }




    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.frag_all_playlist_item, null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final GedanListNeteaseItem item = gedanListItemListNetease.get(position);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(item.getCoverImgUrl()+"?param=140y140"))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(holder.list_image.getController())
                .setImageRequest(request)
                .build();
        holder.list_image.setController(controller);
        holder.list_name.setText(item.getName());
        holder.list_listener.setText(spanString);
        int count = item.getPlayCount();
        if(count > 10000){
            count = count / 10000;
            holder.list_listener.append(" " + count + "万");
        }else {
            holder.list_listener.append(" " + item.getPlayCount());
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, NetPlaylistDetailActivity.class);
//                intent.putExtra("albumid",item.getListid());
//                intent.putExtra("albumart",item.getPic());
//                intent.putExtra("albumname",item.getTitle());
//                mContext.startActivity(intent);
//            }
//        });

    }

    public void update(ArrayList<GedanListNeteaseItem> list){
        this.gedanListItemListNetease = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return gedanListItemListNetease.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView list_listener;
        SimpleDraweeView list_image;
        TextView list_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.list_image = (SimpleDraweeView) itemView.findViewById(R.id.playlist_art);
            this.list_listener = (TextView) itemView.findViewById(R.id.playlist_listen_count);
            this.list_name = (TextView) itemView.findViewById(R.id.playlist_name);
        }
    }
}
