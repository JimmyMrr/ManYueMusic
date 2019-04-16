package com.music.manyue.Activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ritchie_huang.manyuemusic.R;
import com.music.manyue.Service.MusicPlayService;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener {
    Button mStartServiceBtn;
    Button mStopServiceBtn;
    Button mBindServiceBtn;
    Button mUnbindServiceBtn;

    private MusicPlayService.MyBinder mMyBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MusicPlayService.MyBinder) service;
            mMyBinder.onStartDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);

        initViews();
    }

    private void initViews() {
        mStartServiceBtn = findViewById(R.id.btn_start_service);
        mStopServiceBtn = findViewById(R.id.btn_stop_service);
        mBindServiceBtn = findViewById(R.id.btn_bind_service);
        mUnbindServiceBtn = findViewById(R.id.btn_unbind_service);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                startService(new Intent(MusicPlayActivity.this, MusicPlayService.class));
                break;
            case R.id.btn_stop_service:
                stopService(new Intent(MusicPlayActivity.this, MusicPlayService.class));
                break;
            case R.id.btn_bind_service:
                bindService(new Intent(MusicPlayActivity.this, MusicPlayService.class), mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                unbindService(mServiceConnection);
                break;
            default:
                break;
        }
    }
}
