package com.music.manyue.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.manyue.Listener.OnRecyclerItemClickListener;
import com.music.manyue.R;

import java.util.List;


/**
 * Created by ritchie-huang on 16-8-6.
 */
public class PersonalRecommandAdapter extends RecyclerView.Adapter<PersonalRecommandAdapter.ItemViewHolder> {

    private Context mContext;
    private String[] moduleNames;
    private int[] moduleImageIds;
    private List<RecyclerView.Adapter> adapterList;
    private OnRecyclerItemClickListener listener;




    public PersonalRecommandAdapter(Context mContext, String[] moduleNames, int[] moduleImageIds,List<RecyclerView.Adapter> adapterList) {
        this.mContext = mContext;
        this.moduleNames = moduleNames;
        this.moduleImageIds = moduleImageIds;
        this.adapterList = adapterList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recommend_playlist, null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.moduleName.setText(moduleNames[position]);
        holder.moduleImage.setImageResource(moduleImageIds[position]);
        if (listener != null) {
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, position);
                }
            });
        }

        //set
        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setAdapter(adapterList.get(position));

    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return moduleImageIds.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView moduleName;
        ImageView moduleImage;
        RecyclerView recyclerView;
        TextView more;


        public ItemViewHolder(View itemView) {
            super(itemView);
            this.moduleName = (TextView) itemView.findViewById(R.id.module_name);
            this.moduleImage = (ImageView) itemView.findViewById(R.id.module_image);
            this.recyclerView = (RecyclerView) itemView.findViewById(R.id.son_recyclerview);
            this.more = (TextView) itemView.findViewById(R.id.more);
        }
    }
}
