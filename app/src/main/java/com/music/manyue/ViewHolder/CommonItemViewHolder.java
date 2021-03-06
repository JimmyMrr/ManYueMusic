package com.music.manyue.ViewHolder;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.manyue.R;

/**
 * Created by ritchie-huang on 16-8-9.
 */

public class CommonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textView;
    public ImageView select;

    public CommonItemViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.play_all_number);
        this.select = (ImageView) view.findViewById(R.id.select);
        view.setOnClickListener(this);
    }

    public void onClick(View v) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 100);

    }

}
