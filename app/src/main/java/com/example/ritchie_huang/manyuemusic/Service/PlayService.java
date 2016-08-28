package com.example.ritchie_huang.manyuemusic.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.ritchie_huang.manyuemusic.Activity.PlayingActivity;
import com.example.ritchie_huang.manyuemusic.DataItem.GedanNeteaseDetailItem;
import com.example.ritchie_huang.manyuemusic.DataItem.MP3InfoItem;
import com.example.ritchie_huang.manyuemusic.Lyric.GetLyric;
import com.example.ritchie_huang.manyuemusic.Lyric.LyricContent;
import com.example.ritchie_huang.manyuemusic.Util.Api;
import com.example.ritchie_huang.manyuemusic.Util.Constants;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class PlayService<T> extends Service {

    private MediaPlayer mMediaPlayer;
    private String mMusicPath;
    private int mMusicPlayerStatus = Constants.STATUS_ORDER;
    private boolean isPaused;
    private boolean isPlaying;
    private List<T> mSongList;
    private int current = 0;
    private int currentTime;
    private int musicDuration;
    private MyReceiver mMyReceiver;

    //lyric
    private GetLyric mGetLyric;
    private List<LyricContent> mLyricContentList = new ArrayList<>();
    private int index = 0;
    private PlayingActivity mPlayingActivity;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mMediaPlayer != null) {
                    currentTime = mMediaPlayer.getCurrentPosition();
                    Intent intent = new Intent();
                    intent.setAction(Constants.MUSIC_CURRENT);
                    intent.putExtra("currentTime", currentTime);
                    sendBroadcast(intent);
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();


        //播放完成监听
        mMediaPlayer.setOnCompletionListener(new MyCompletionListener());

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayMusicBinder();
    }

    private class PrepareListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PrepareListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
            if (currentTime > 0) {
                mediaPlayer.seekTo(0);

            }
            Intent intent = new Intent();
            intent.setAction(Constants.MUSIC_DURATION);
            musicDuration = mediaPlayer.getDuration();
            intent.putExtra("duration", musicDuration);
            sendBroadcast(intent);
        }
    }


    private class MyReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getIntExtra("control", -1);
            switch (status) {
                case Constants.STATUS_LOOP_ONE:
                    mMusicPlayerStatus = Constants.STATUS_LOOP_ONE;
                    break;
                case Constants.STATUS_ORDER:
                    mMusicPlayerStatus = Constants.STATUS_ORDER;
                    break;
                case Constants.STATUS_RANDOM:
                    mMusicPlayerStatus = Constants.STATUS_RANDOM;
                    break;
                default:
                    break;
            }
        }
    }


    public class PlayMusicBinder extends Binder {

        public PlayService getPlayService() {
            return PlayService.this;
        }

        public void setPlayingPath(int num) {
            GedanNeteaseDetailItem.ResultBean.TracksBean currentSong = (GedanNeteaseDetailItem.ResultBean.TracksBean) mSongList.get(num);
            mMusicPath = currentSong.getMp3Url();
        }

        public void setPlayList(List<T> list) {
            mSongList = list;
        }

        public void setCurrent(int num) {
            current = num;
        }

        public void startPlay(String dataSource) {
            mMusicPath = dataSource;
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(dataSource);
                mMediaPlayer.prepare();
                mMediaPlayer.start();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        public void pause() {
            if (isPaused) {
                mMediaPlayer.start();
                isPaused = false;
            }
        }

        public void previous() {
            Intent intent = new Intent(Constants.UPDATE_ACTION);
            current--;
            if (current < 0) {
                intent.putExtra("current", current);
                setPlayingPath(current);
                sendBroadcast(intent);
                play(0);

            } else {
                Toast.makeText(mPlayingActivity, "错误", Toast.LENGTH_SHORT).show();

            }

        }

        public void next() {
            Intent intent = new Intent(Constants.UPDATE_ACTION);
            current++;
            if (current < mSongList.size()) {
                setPlayingPath(current);
                intent.putExtra("current", current);
                sendBroadcast(intent);
                play(0);
            } else {
                Toast.makeText(mPlayingActivity, "错误", Toast.LENGTH_SHORT).show();

            }

        }

        public void stop() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
            }
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //顺序播放
            if (mMusicPlayerStatus == Constants.STATUS_ORDER) {
                current++;
                if (current <= mSongList.size() - 1) {
                    Intent intent = new Intent(Constants.UPDATE_ACTION);
                    sendBroadcast(intent);
//                        mMusicPath = mSongList.get(current).getPath();
                    play(0);
                } else {
                    mediaPlayer.seekTo(0);
                    current = 0;
                    Intent intent = new Intent(Constants.UPDATE_ACTION);
                    intent.putExtra("current", current);
                    sendBroadcast(intent);

                }
            } else if (mMusicPlayerStatus == Constants.STATUS_RANDOM) {//随机播放
                current = getRandomIndex(mSongList.size() - 1);
                Intent intent = new Intent(Constants.UPDATE_ACTION);
                intent.putExtra("current", current);
                sendBroadcast(intent);


            } else {//单曲循环
                mMediaPlayer.start();
            }
        }
    }


    //播放
    private void play(int i) {
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mMusicPath);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new PrepareListener(i));
            mHandler.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private int getRandomIndex(int i) {
        int result = (int) (Math.random() * i);
        return result;
    }

    public void setPlayingActivity(PlayingActivity activity) {
        this.mPlayingActivity = activity;
    }

    private void initLrc() {
        mGetLyric = new GetLyric();
        GedanNeteaseDetailItem.ResultBean.TracksBean localItem = (GedanNeteaseDetailItem.ResultBean.TracksBean) mSongList.get(current);
        final RequestBody formbody = new FormEncodingBuilder()
                .add("os", "pc")
                .add("id", String.valueOf(localItem.getId()))
                .add("lv", String.valueOf(-1))
                .add("kv", String.valueOf(-1))
                .add("tv", String.valueOf(-1))
                .build();

        mGetLyric.readLyric(Api.SONG_LRC, formbody, getApplicationContext(), false);
        mLyricContentList = mGetLyric.getLyricContentList();

        mPlayingActivity.lyric.setMyLyricList(mLyricContentList);
        mHandler.post(mRunnable);


    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mPlayingActivity.lyric.setIndex(lrcIndex());
            mPlayingActivity.lyric.invalidate();
            mHandler.postDelayed(mRunnable, 100);
        }
    };

    public int lrcIndex() {
        if (mMediaPlayer.isPlaying()) {
            currentTime = mMediaPlayer.getCurrentPosition();
            musicDuration = mMediaPlayer.getDuration();
        }
        if (currentTime < musicDuration) {
            for (int i = 0; i < mLyricContentList.size(); i++) {
                if (i < mLyricContentList.size() - 1) {
                    if (currentTime < mLyricContentList.get(i).getLyricTime() && i == 0) {
                        index = i;
                    }
                    if (currentTime > mLyricContentList.get(i).getLyricTime()
                            && currentTime < mLyricContentList.get(i + 1).getLyricTime()) {
                        index = i;
                    }
                }
                if (i == mSongList.size() - 1
                        && currentTime > mLyricContentList.get(i).getLyricTime()) {
                    index = i;
                }
            }
        }
        return index;
    }


}

