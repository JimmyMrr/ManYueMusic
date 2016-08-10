package com.example.ritchie_huang.manyuemusic.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.DataItem.GedanBMADetailItem;
import com.example.ritchie_huang.manyuemusic.DataItem.GedanSrcBMA;
import com.example.ritchie_huang.manyuemusic.DataItem.MusicDetailNet;
import com.example.ritchie_huang.manyuemusic.DataItem.MusicInfoItem;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.BMA;
import com.example.ritchie_huang.manyuemusic.Util.HttpUtil;
import com.example.ritchie_huang.manyuemusic.Util.ImageUtils;
import com.example.ritchie_huang.manyuemusic.ViewHolder.CommonItemViewHolder;
import com.example.ritchie_huang.manyuemusic.Widget.DividerItemDecoration;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HotNewsAlbumDetailActivity extends AppCompatActivity {
    Gson mGson;
    private long mPlayListId;
    private String mAlbumPath, mAlbumName, mAlbumDes, mArtistName;
    private int publishTime;

    private List<MusicInfoItem> mList;
    private SimpleDraweeView albumSmallPic;
    private ImageView albumArt;
    private TextView albumName, albumDes;
    private RecyclerView mRecyclerView;
    private PlaylistDetailAdapter mAdapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGson = new Gson();
        if (getIntent().getExtras() != null) {
            mPlayListId = getIntent().getLongExtra("albumid",-1);
            mAlbumName = getIntent().getStringExtra("albumname");
            mAlbumPath = getIntent().getStringExtra("albumart");
            mAlbumDes = getIntent().getStringExtra("albumdesc");
            mArtistName = getIntent().getStringExtra("artistname");
            publishTime = getIntent().getIntExtra("publishtime",-1);
        }
        setContentView(R.layout.activity_gedandetail);

        initViews();


    }


    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_forplaying);
        toolbar.setTitle(mArtistName);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        albumDes = (TextView) findViewById(R.id.album_details);
        albumName = (TextView) findViewById(R.id.album_title);
        albumSmallPic = (SimpleDraweeView) findViewById(R.id.albumArtSmall);
        albumArt = (ImageView) findViewById(R.id.album_art);

        albumDes.setText(mAlbumDes);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpEveryThing();
    }

    private void setUpEveryThing() {
        loadAllLists();
        setAlbumArt();
    }

    private void setAlbumArt() {
        albumName.setText(mAlbumName);
        albumSmallPic.setImageURI(Uri.parse(mAlbumPath));
        try {
            //drawable = Drawable.createFromStream( new URL(albumPath).openStream(),"src");
            ImageRequest imageRequest = ImageRequest.fromUri(mAlbumPath);
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                    .getEncodedCacheKey(imageRequest, null);
            BinaryResource resource = ImagePipelineFactory.getInstance()
                    .getMainDiskStorageCache().getResource(cacheKey);
            File file = ((FileBinaryResource) resource).getFile();
            new setBlurredAlbumArt().execute(ImageUtils.getArtworkQuick(file, 300, 300));


        } catch (Exception e) {

        }

    }

    private class setBlurredAlbumArt extends AsyncTask<Bitmap, Void, Drawable> {

        @Override
        protected Drawable doInBackground(Bitmap... loadedImage) {
            Drawable drawable = null;

            try {
                drawable = ImageUtils.createBlurredImageFromBitmap(loadedImage[0], HotNewsAlbumDetailActivity.this, 20);
//                drawable = ImageUtils.createBlurredImageFromBitmap(ImageUtils.getBitmapFromDrawable(Drawable.createFromStream(new URL(albumPath).openStream(), "src")),
//                        NetPlaylistDetailActivity.this, 30);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                if (albumArt.getDrawable() != null) {
                    final TransitionDrawable td =
                            new TransitionDrawable(new Drawable[]{
                                    albumArt.getDrawable(),
                                    result
                            });
                    albumArt.setImageDrawable(td);
                    td.startTransition(200);

                } else {
                    albumArt.setImageDrawable(result);
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }

    GedanSrcBMA gedanSrcBMA;
    MusicDetailNet musicDetailNet;

    private void loadAllLists() {
        mList = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {

                try {
                    String action = "http://music.163.com/api/album/" + String.valueOf(mPlayListId);


                    JsonArray jsonArray = HttpUtil.getResposeJsonObject(action).get("album").getAsJsonObject()
                            .get("songs").getAsJsonArray();

                    Iterator it = jsonArray.iterator();
                    while(it.hasNext()){
                        JsonElement e = (JsonElement)it.next();
                        JsonObject jo = e.getAsJsonObject();
                        MusicInfoItem mi = new MusicInfoItem();
                        mi.url = getStringValue(jo, "mp3Url");
                        mi.musicName = getStringValue(jo, "name");

                        mList.add(mi);
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter = new PlaylistDetailAdapter(HotNewsAlbumDetailActivity.this, mList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(HotNewsAlbumDetailActivity.this, DividerItemDecoration.VERTICAL_LIST));
            }
        }.execute();


    }

    private String getStringValue(JsonObject jsonObject, String key) {
        JsonElement nameElement = jsonObject.get(key);
        return nameElement.getAsString();
    }

    private int getIntValue(JsonObject jsonObject, String key) {
        JsonElement nameElement = jsonObject.get(key);
        return nameElement.getAsInt();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_forplaying, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchIcon) {


            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    class PlaylistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int FIRST_ITEM = 0;
        final static int ITEM = 1;
        private List<MusicInfoItem> arraylist;
        private long playlistId;
        private Activity mContext;

        public PlaylistDetailAdapter(Activity context, List<MusicInfoItem> mList) {
            this.arraylist = mList;
            this.mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == FIRST_ITEM) {
                return new CommonItemViewHolder(LayoutInflater.from(HotNewsAlbumDetailActivity.this).inflate(R.layout.common_item, viewGroup, false));
            } else {
                return new ItemViewHolder(LayoutInflater.from(HotNewsAlbumDetailActivity.this).inflate(R.layout.fragment_playlist_detail_item, viewGroup, false));
            }

        }

        //判断布局类型
        @Override
        public int getItemViewType(int position) {
            return position == FIRST_ITEM ? FIRST_ITEM : ITEM;

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder itemHolder, final int i) {
            if (itemHolder instanceof ItemViewHolder) {
                final MusicInfoItem localItem = arraylist.get(i - 1);
                ((ItemViewHolder) itemHolder).trackNumber.setText(i + "");
                ((ItemViewHolder) itemHolder).title.setText(localItem.musicName);
                ((ItemViewHolder) itemHolder).artist.setText(mArtistName);
                ((ItemViewHolder) itemHolder).menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            } else if (itemHolder instanceof CommonItemViewHolder) {

                ((CommonItemViewHolder) itemHolder).textView.setText("(共" + arraylist.size() + "首)");

                ((CommonItemViewHolder) itemHolder).select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            return (null != arraylist ? arraylist.size() + 1 : 0);
        }

        public void updateDataSet(long playlistid, ArrayList<MusicInfoItem> arraylist) {
            this.arraylist = arraylist;
            this.playlistId = playlistid;
        }


        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected TextView title, artist, trackNumber;
            protected ImageView menu;

            public ItemViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.song_title);
                this.artist = (TextView) view.findViewById(R.id.song_artist);
                this.trackNumber = (TextView) view.findViewById(R.id.trackNumber);
                this.menu = (ImageView) view.findViewById(R.id.popup_menu);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        try {


                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(musicDetailNet.getShow_link());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
//                            MusicPlayer.clearQueue();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 100);

            }

        }
    }

}
