package com.example.ritchie_huang.manyuemusic.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.SeekBar;

import com.example.ritchie_huang.manyuemusic.Lyric.GetLyric;
import com.example.ritchie_huang.manyuemusic.Lyric.LyricView;
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Service.PlayService;
import com.example.ritchie_huang.manyuemusic.Util.Api;
import com.example.ritchie_huang.manyuemusic.Util.Constants;
import com.example.ritchie_huang.manyuemusic.Util.HttpUtil;
import com.example.ritchie_huang.manyuemusic.Util.SongUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.vistrav.ask.Ask;

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

    private PlayService.PlayMusicBinder mBinder;
    private ServiceConnection mConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
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


//        setLyric();

//        mConnection = new ConnectionService();
//        bindService(new Intent(PlayingActivity.this, PlayService.class), mConnection, -1);
        setUpEverything();

        musicDuration.setText(SongUtil.formatDuration(songDuration));
        song_name.setText(songName);
        song_artist.setText(songArtist);

        setSongBackgroundImage();


    }

    class ConnectionService implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = (PlayService.PlayMusicBinder) iBinder;
//            mPlayService.setPlayingActivity(PlayingActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    private void setUpEverything() {
        playingMode.setOnClickListener(this);

    }

    private void setLyric() {
        final RequestBody formbody = new FormEncodingBuilder()
                .add("os", "pc")
                .add("id", String.valueOf(songLyricId))
                .add("lv", String.valueOf(-1))
                .add("kv", String.valueOf(-1))
                .add("tv", String.valueOf(-1))
                .build();


        new GetLyric().readLyric(Api.SONG_LRC, formbody, PlayingActivity.this, false);


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
    }
}
