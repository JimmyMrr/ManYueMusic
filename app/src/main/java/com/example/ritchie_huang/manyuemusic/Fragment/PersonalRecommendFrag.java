package com.example.ritchie_huang.manyuemusic.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.ritchie_huang.manyuemusic.Activity.HomeActivity;
import com.example.ritchie_huang.manyuemusic.DataItem.BannerItem;
import com.example.ritchie_huang.manyuemusic.R;
import com.facebook.drawee.view.SimpleDraweeView;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-2.
 */
public class PersonalRecommendFrag extends Fragment {
    private ConvenientBanner<BannerItem> convenientBanner;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    private List<BannerItem> list = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_personal_recommand, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        convenientBanner = (ConvenientBanner<BannerItem>) view.findViewById(R.id.convenientBanner);
        for (String imag : images) {
            BannerItem bannerItem = new BannerItem();
            bannerItem.setBanner_image_url(imag);
            list.add(bannerItem);
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {

            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, list)
                .setPageIndicator(new int[]{R.mipmap.ic_radio_button_unchecked_white_24dp, R.mipmap.ic_radio_button_checked_white_24dp})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }


    private class NetworkImageHolderView implements Holder<BannerItem> {

        private View view;

        @Override
        public View createView(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerItem data) {
            ((SimpleDraweeView) view.findViewById(R.id.banner_image)).setImageURI(Uri.parse(data.getBanner_image_url()));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
