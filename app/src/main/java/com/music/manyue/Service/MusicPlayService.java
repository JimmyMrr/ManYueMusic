package com.music.manyue.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicPlayService extends Service {

    private static final String TAG = "MusicPlayService";
    private MyBinder mMyBinder = new MyBinder();


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: Service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: Service");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Service");
        return mMyBinder;
    }


    public class MyBinder extends Binder {
        public void onStartDownload() {
            Log.d(TAG, "onStartDownload: 开始下载kkkk");
        }
    }
}
