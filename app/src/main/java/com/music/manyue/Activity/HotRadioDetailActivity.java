package com.music.manyue.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.music.manyue.DataItem.MusicDetailNet;
import com.music.manyue.DataItem.MusicInfoItem;
import com.music.manyue.R;
import com.music.manyue.Util.BMA;
import com.music.manyue.Util.HttpUtil;
import com.music.manyue.ViewHolder.CommonItemViewHolder;
import com.music.manyue.Widget.DividerItemDecoration;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

public class HotRadioDetailActivity extends AppCompatActivity {

    Gson mGson;
    private String mPlayListId;
    private String mAlbumPath, mAlbumName, mAlbumDes, mArtistName;
    private List<MusicInfoItem> mList;
    private SimpleDraweeView albumSmallPic;
    private SimpleDraweeView albumArt;
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
            mPlayListId = getIntent().getStringExtra("albumid");
            mAlbumName = getIntent().getStringExtra("albumname");
            mAlbumPath = getIntent().getStringExtra("albumart");
            mAlbumDes = getIntent().getStringExtra("albumdesc");
            mArtistName = getIntent().getStringExtra("artistname");
        }
        setContentView(R.layout.activity_gedandetail);

        initViews();


    }


    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_forplaying);
        toolbar.setTitle("电台");
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
        albumArt = (SimpleDraweeView) findViewById(R.id.album_art);

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

        ImageRequest request =
                ImageRequestBuilder.fromRequest(ImageRequest.fromUri(Uri.parse(mAlbumPath)))
                        .setResizeOptions(new ResizeOptions(50, 50))
                        .setPostprocessor(new BlurPostprocessor(this, 12, 2))
                        .build();

        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(albumArt.getController())
                        .build();
        albumArt.setController(controller);


    }


    MusicDetailNet musicDetailNet;

    private void loadAllLists() {
        mList = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {

                try {
                    JsonObject jsonObject = HttpUtil.getResposeJsonObject(BMA.Lebo.albumInfo(mPlayListId, 10)).get("result").getAsJsonObject();
                    JsonArray pArray = jsonObject.get("latest_song").getAsJsonArray();
                    int plen = pArray.size();

                    Iterator it = pArray.iterator();
                    while (it.hasNext()) {
                        JsonElement e = (JsonElement) it.next();
                        JsonObject jo = e.getAsJsonObject();
                        MusicInfoItem mi = new MusicInfoItem();
                        mi.artist = getStringValue(jo, "song_duration");
                        mi.musicName = getStringValue(jo, "song_name");
                        mi.songId = Integer.parseInt(getStringValue(jo, "song_id"));

                        mList.add(mi);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter = new PlaylistDetailAdapter(HotRadioDetailActivity.this, mList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(HotRadioDetailActivity.this, DividerItemDecoration.VERTICAL_LIST));
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
                return new CommonItemViewHolder(LayoutInflater.from(HotRadioDetailActivity.this).inflate(R.layout.common_item, viewGroup, false));
            } else {
                return new ItemViewHolder(LayoutInflater.from(HotRadioDetailActivity.this).inflate(R.layout.fragment_playlist_detail_item, viewGroup, false));
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


        public class ItemViewHolder extends RecyclerView.ViewHolder {
            protected TextView title, artist, trackNumber;
            protected ImageView menu;

            public ItemViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.song_title);
                this.artist = (TextView) view.findViewById(R.id.song_artist);
                this.trackNumber = (TextView) view.findViewById(R.id.trackNumber);
                this.menu = (ImageView) view.findViewById(R.id.popup_menu);
            }


        }
    }
}

