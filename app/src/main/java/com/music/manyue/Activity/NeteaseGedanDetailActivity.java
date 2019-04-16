package com.music.manyue.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.manyue.DataItem.NeteaseSongList;
import com.music.manyue.DataItem.GedanSrcBMA;
import com.music.manyue.DataItem.MusicDetailNet;
import com.example.ritchie_huang.manyuemusic.R;
import com.music.manyue.Service.PlayService;
import com.music.manyue.API.API;
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
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

/**
 * Created by ritchie-huang on 16-8-11.
 */
public class NeteaseGedanDetailActivity extends AppCompatActivity {
    private static final String TAG = "NeteaseGedanDetailActiv";
    Gson mGson;
    private String mPlayListId;
    private String mAlbumPath, mAlbumName, mAlbumDes;
    private List<NeteaseSongList> mList;
    private SimpleDraweeView albumSmallPic;
    private SimpleDraweeView albumArt;
    private TextView albumName, albumDes;
    private RecyclerView mRecyclerView;
    private PlaylistDetailAdapter mAdapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    ServiceConnection conn;


    private PlayService.PlayMusicBinder mBinder;
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
        }
        setContentView(R.layout.activity_gedandetail);

        initViews();

//        Intent intent = new Intent(this, PlayService.class);


//        conn = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//                mBinder = (PlayService.PlayMusicBinder) iBinder;
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//
//            }
//        };
//        bindService(intent,conn,BIND_AUTO_CREATE);


    }


    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_forplaying);
        toolbar.setTitle("");
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


    GedanSrcBMA gedanSrcBMA;
    MusicDetailNet musicDetailNet;

    private void loadAllLists() {
        mList = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {

                try {
                    JsonObject jsonObject = HttpUtil.getResposeJsonObject(API.GEDAN_DETAIL + mPlayListId + "");
                    NeteaseSongList geDanGeInfo = mGson.fromJson(jsonObject, NeteaseSongList.class);


                    mList.add(geDanGeInfo);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter = new PlaylistDetailAdapter(NeteaseGedanDetailActivity.this, mList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(NeteaseGedanDetailActivity.this, DividerItemDecoration.VERTICAL_LIST));
            }
        }.execute();


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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class PlaylistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int FIRST_ITEM = 0;
        final static int ITEM = 1;
        private List<NeteaseSongList> arraylist;
        private long playlistId;
        private Activity mContext;

        public PlaylistDetailAdapter(Activity context, List<NeteaseSongList> mList) {
            this.arraylist = mList;
            this.mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == FIRST_ITEM) {
                return new CommonItemViewHolder(LayoutInflater.from(NeteaseGedanDetailActivity.this).inflate(R.layout.common_item, viewGroup, false));
            } else {
                return new ItemViewHolder(LayoutInflater.from(NeteaseGedanDetailActivity.this).inflate(R.layout.fragment_playlist_detail_item, viewGroup, false));
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
                final NeteaseSongList.ResultBean.TracksBean localItem = arraylist.get(0).getResult().getTracks().get(i - 1);
                ((ItemViewHolder) itemHolder).trackNumber.setText(i + "");
                ((ItemViewHolder) itemHolder).title.setText(localItem.getName());
                ((ItemViewHolder) itemHolder).artist.setText(localItem.getArtists().get(0).getName());
                ((ItemViewHolder) itemHolder).menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PlayService.setPlayList(arraylist.get(0).getResult().getTracks());
                        Intent intent = new Intent(NeteaseGedanDetailActivity.this, PlayingActivity.class);
                        intent.putExtra("songItem", i - 1);
                        int song_id = localItem.getId();
                        String song_mp3_url = API.SONG_MP3_URL + song_id + ".mp3";
                        intent.putExtra("songUrl", song_mp3_url);
                        intent.putExtra("songBackgroundImage", localItem.getAlbum().getPicUrl());
                        intent.putExtra("songLyric", localItem.getId());
                        intent.putExtra("songName", localItem.getName());
                        intent.putExtra("songArtist", localItem.getArtists().get(0).getName());
                        intent.putExtra("songDuration", localItem.getDuration());
                        startActivity(intent);
                    }
                });

            } else if (itemHolder instanceof CommonItemViewHolder) {

                ((CommonItemViewHolder) itemHolder).textView.setText("(共" + arraylist.get(0).getResult().getTracks().size() + "首)");

                ((CommonItemViewHolder) itemHolder).select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount: " + arraylist.get(0).getResult());
            List<NeteaseSongList.ResultBean.TracksBean> list = null;
            try {
                list = arraylist.get(0).getResult().getTracks();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null == list ? 0 : list.size() + 1;
        }

        public void updateDataSet(long playlistid, ArrayList<NeteaseSongList> arraylist) {
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
