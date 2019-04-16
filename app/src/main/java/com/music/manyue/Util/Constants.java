package com.music.manyue.Util;

/**
 * Created by ritchie-huang on 16-8-2.
 */
public class Constants {
    //网络请求失败
    public static final int OKHTTP_CALL_FAIL = 0x01;
    //网络请求成功
    public static final int OKHTTP_CALL_SUCCESS = 0x02;

    //广播动作-点击列表播放
    public static final String ACTION_PLAY_LIST = "com.ritchie.action.ACTION_PLAY_LIST";
    //广播动作-播放
    public static final String ACTION_PLAY = "com.ritchie.action.ACTION_PLAY";
    //广播动作-暂停
    public static final String ACTION_PAUSE = "com.ritchie.action.ACTION_PAUSE";
    //广播动作-上一首
    public static final String ACTION_PREVIOUS = "com.ritchie.action.ACTION_PREVIOUS";
    //广播动作-下一首
    public static final String ACTION_NEXT = "com.ritchie.action.ACTION_NEXT";


    //服务发送动作
    //更新
    public static final String UPDATE_ACTION = "com.ritchie.action.SERVICE_UPDATE_ACTION";
    //控制
    public static final String CONTROL_ACTION = "com.ritchie.action.SERVICE_CONTROL_ACTION";
    //更新播放时间
    public static final String MUSIC_CURRENT = "com.ritchie.action.SERVICE_MUSIC_CURRENT";
    //更新音乐长度
    public static final String MUSIC_DURATION = "com.ritchie.action.SERVICE_MUSIC_DURATION";






    //歌曲播放状态
    public static final int STATUS_ORDER = 1;
    public static final int STATUS_RANDOM = 2;
    public static final int STATUS_LOOP_ONE = 3;

}
