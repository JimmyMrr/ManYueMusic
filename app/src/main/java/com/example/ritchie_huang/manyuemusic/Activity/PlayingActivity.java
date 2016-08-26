package com.example.ritchie_huang.manyuemusic.Activity;

import android.os.Bundle;
import android.app.Activity;
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
import com.example.ritchie_huang.manyuemusic.R;
import com.example.ritchie_huang.manyuemusic.Util.Api;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

public class PlayingActivity extends AppCompatActivity {

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
    private TextView lyric;

    //Toolbar控件


    private String albumArtUrl;
    private int songLyricId;
    private String songLyric;
    private String songName;
    private String songArtist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            albumArtUrl = getIntent().getStringExtra("songBackgroundImage");
            songLyricId = getIntent().getIntExtra("songLyric",-1);
            Log.d("PlayingActivity", "songLyricId:" + songLyricId);
            songName = getIntent().getStringExtra("songName");
            songArtist = getIntent().getStringExtra("songArtist");
            Log.d("PlayingActivity", albumArtUrl);
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


        setLyric();
        song_name.setText(songName);
        song_artist.setText(songArtist);
        lyric.setText(songLyric);

        setSongBackgroundImage();


    }

    private void setLyric() {
        RequestBody formbody = new FormEncodingBuilder()
                .add("os", "pc")
                .add("id", String.valueOf(songLyricId))
                .add("lv", String.valueOf(-1))
                .add("kv", String.valueOf(-1))
                .add("tv", String.valueOf(-1))
                .build();

        songLyric = new GetLyric().getLyricString(Api.SONG_LRC,formbody,PlayingActivity.this,false);

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
        lrcScrollView = (ScrollView) findViewById(R.id.lrcScrollView);
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

        lyric = (TextView) findViewById(R.id.lyric);

    }

}
