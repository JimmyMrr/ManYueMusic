package com.example.ritchie_huang.manyuemusic.Lyric;

import android.content.Context;
import android.util.Log;

import com.example.ritchie_huang.manyuemusic.Util.HttpUtil;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-22.
 */
public class GetLyric {
    private List<LyricContent> mLyricContentList;
    private LyricContent mLyricContent;
/**
 {"sgc":true,
 "sfy":false,
 "qfy":false,
 "lrc":{"version":9,
 "lyric":"[00:00.00] 作曲 : 曹方\n
 [00:01.00] 作词 : 曹方\n
 [00:33.340]南部小城 没有光彩照人\n
 [00:40.300]每次我会来这里 我都感觉着平静\n
 [00:47.100]阳光炽烈 人们慢悠悠的步子\n
 [00:53.760]零落的草帽 我栽的花儿\n
 [01:00.500]摇啊摇 摇啊摇 摇啊摇 摇啊摇\n
 [01:13.760]\n
 [01:14.180]我在这里一个人唱这首歌\n
 [01:20.770]人们只是微笑 哦 微笑\n
 [01:27.510]我在这里一个人唱这首歌\n
 [01:34.700]你不会知道 哦 知道\n
 [01:41.600]\n
 [02:08.529]南部小城 光阴缓流的城\n
 [02:15.140]每次我回到这里 你都那么的恬静\n
 [02:22.390]赞美夏天 女孩摇曳的裙摆\n
 [02:28.839]撩动了昨天 荡着的秋千\n
 [02:35.549]摇啊摇 摇啊摇 摇啊摇 摇啊摇\n
 [02:48.859]\n
 [02:49.219]我在这里一个人唱这首歌\n
 [02:55.939]人们只是微笑 哦 微笑\n
 [03:02.769]我在这里一个人唱这首歌\n
 [03:09.609]你不会知道 哦 知道\n
 [03:16.249]我在这里一个人唱这首歌\n
 [03:23.119]人们只是微笑 哦 微笑\n
 [03:29.859]我在这里一个人唱这首歌\n
 [03:36.739]你不会知道 哦 知道\n
 [03:45.289]\n
 [03:47.659]\n"},
 "klyric":{"version":3,"lyric":null},"tlyric":{"version":0},"code":200}
y */
    public GetLyric() {
        mLyricContentList = new ArrayList<>();
        mLyricContent = new LyricContent();
    }


    public String getLyricString(String url, RequestBody formbody, Context context,boolean forceCache) {

        String lyricString = HttpUtil.PostResposeJsonObject(url, formbody, context, forceCache).getAsJsonObject("lrc").get("lyric").getAsString();
        Log.d("GetLyric", lyricString);
        return lyricString;
    }


    public void readLyric(String url, RequestBody formbody, Context context, boolean forceCache) {

    }


}