package com.example.ritchie_huang.manyuemusic.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.R;

import java.util.List;


/**
 * Created by ritchie-huang on 16-8-6.
 */
public class PersonalRecommandAdapter extends RecyclerView.Adapter<PersonalRecommandAdapter.ItemViewHolder> {

    private Context mContext;
    private String[] moduleNames;
    private int[] moduleImageIds;
    private List<RecyclerView.Adapter> adapterList;


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
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.moduleName.setText(moduleNames[position]);
        holder.moduleImage.setImageResource(moduleImageIds[position]);
        //set
//        holder.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
//        holder.recyclerView.setAdapter(adapterList.get(position));

    }

    @Override
    public int getItemCount() {
        return moduleImageIds.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView moduleName;
        ImageView moduleImage;
        RecyclerView recyclerView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            this.moduleName = (TextView) itemView.findViewById(R.id.module_name);
            this.moduleImage = (ImageView) itemView.findViewById(R.id.module_image);
            this.recyclerView = (RecyclerView) itemView.findViewById(R.id.son_recyclerview);
        }
    }
}
