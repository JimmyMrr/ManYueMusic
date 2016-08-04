package com.example.ritchie_huang.manyuemusic.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.SeekBar;

import com.example.ritchie_huang.manyuemusic.R;

public class PlayingActivity extends AppCompatActivity {

    private ImageView albumArt;
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

    //Toolbar控件




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_forplaying);
        toolbar.setTitle("本地音乐");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initViews();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_forplaying,menu);

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
        albumArt = (ImageView) findViewById(R.id.albumArt);
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


    }

}
