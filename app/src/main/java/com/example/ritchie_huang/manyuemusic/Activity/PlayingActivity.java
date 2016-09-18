package com.example.ritchie_huang.manyuemusic.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ritchie_huang.manyuemusic.DataItem.GedanNeteaseDetailItem;
import com.example.ritchie_huang.manyuemusic.Lyric.LyricView;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Service.PlayService;
import com.example.ritchie_huang.manyuemusic.Util.Constants;
import com.example.ritchie_huang.manyuemusic.Util.SongUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {


        }
    };

    private SimpleDraweeView albumArt;
    private FrameLayout headerView;
    private ScrollView lrcScrollView;
    private TextView musicDurationPlayed;
    private SeekBar playSeek;
    private TextView musicDuration;
    private ImageView playingMode;
    private ImageView playingPre;
    private ImageView playingPlay;
    private ImageView playingNext;
    private ImageView playingPlaylist;
    private TextView song_name;
    private TextView song_artist;
    public LyricView lyric;

    //Toolbar控件


    private String albumArtUrl;
    private int songLyricId;
    private int songDuration;
    private String songName;
    private String songArtist;
    private String songUrl;
    private int songItem;
    private List<GedanNeteaseDetailItem.ResultBean.TracksBean> mList;

    //service

    private PlayService.PlayMusicBinder mBinder;
    private ServiceConnection mConnection;
    private PlayService mPlayService;

    private int[] playingModeId = { R.mipmap.play_icn_one_prs, R.mipmap.play_icn_loop_prs,R.mipmap.play_icn_shuffle};
    private int[] playingModeControl = {Constants.STATUS_LOOP_ONE,Constants.STATUS_ORDER,Constants.STATUS_RANDOM};

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            songUrl = getIntent().getStringExtra("songUrl");
            songItem = getIntent().getIntExtra("songItem", -1);
            albumArtUrl = getIntent().getStringExtra("songBackgroundImage");
            songLyricId = getIntent().getIntExtra("songLyric", -1);
            songDuration = getIntent().getIntExtra("songDuration", -1);
            songName = getIntent().getStringExtra("songName");
            songArtist = getIntent().getStringExtra("songArtist");
        }

        setContentView(R.layout.activity_playing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_forplaying);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initViews();


        Intent intent = new Intent(PlayingActivity.this, PlayService.class);
        mConnection = new ConnectionService();
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        setOnClickListener();

        musicDuration.setText(SongUtil.formatDuration(songDuration));
        song_name.setText(songName);
        song_artist.setText(songArtist);

        setSongBackgroundImage();


    }



    class ConnectionService implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = (PlayService.PlayMusicBinder) iBinder;
            mPlayService = mBinder.getPlayService();
            mPlayService.setPlayingActivity(PlayingActivity.this);
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    mBinder.setCurrent(songItem);
                    mBinder.startPlay(songUrl,songLyricId);
//                    mPlayService.initLrc();

                }


            }.start();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    private void setOnClickListener() {
        playingMode.setOnClickListener(this);
        playingNext.setOnClickListener(this);
        playingPre.setOnClickListener(this);
        playingPlay.setOnClickListener(this);
    }



    private void setSongBackgroundImage() {
        ImageRequest request = ImageRequestBuilder.fromRequest(ImageRequest.fromUri(albumArtUrl))
                .setResizeOptions(new ResizeOptions(200, 300))
                .setPostprocessor(new BlurPostprocessor(this, 20, 4))
                .build();
        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(albumArt.getController())
                        .build();
        albumArt.setController(controller);
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


    private void initViews() {
        albumArt = (SimpleDraweeView) findViewById(R.id.albumArt);
        headerView = (FrameLayout) findViewById(R.id.headerView);
        lyric = (LyricView) findViewById(R.id.lyricView);
        musicDurationPlayed = (TextView) findViewById(R.id.music_duration_played);
        playSeek = (SeekBar) findViewById(R.id.play_seek);
        musicDuration = (TextView) findViewById(R.id.music_duration);
        playingMode = (ImageView) findViewById(R.id.playing_mode);
        playingPre = (ImageView) findViewById(R.id.playing_pre);
        playingPlay = (ImageView) findViewById(R.id.playing_play);
        playingNext = (ImageView) findViewById(R.id.playing_next);
        playingPlaylist = (ImageView) findViewById(R.id.playing_playlist);
        song_name = (TextView) findViewById(R.id.song_name);
        song_artist = (TextView) findViewById(R.id.song_artist);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_mode:
                i++;
                playingMode.setImageResource(playingModeId[i%3]);
                Intent intent = new Intent(this, PlayService.class);
                intent.setAction(Constants.CONTROL_ACTION);
                intent.putExtra("control", playingModeControl[i%3]);
                sendBroadcast(intent);
                break;

            case R.id.playing_next:
                mBinder.next();
                break;
            case R.id.playing_pre:
                mBinder.previous();
                break;
            case R.id.playing_play:
                mBinder.pause();
                break;

            default:
                break;
        }
    }



    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String current = intent.getStringExtra("current");
            if (action.equals(Constants.UPDATE_ACTION)) {


            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
