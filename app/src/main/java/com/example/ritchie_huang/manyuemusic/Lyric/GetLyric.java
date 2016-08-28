package com.example.ritchie_huang.manyuemusic.Lyric;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
     * {"sgc":true,
     * "sfy":false,
     * "qfy":false,
     * "lrc":{"version":9,
     * "lyric":"[00:00.00] 作曲 : 曹方\n
     * [00:01.00] 作词 : 曹方\n
     * [00:33.340]南部小城 没有光彩照人\n
     * [00:40.300]每次我会来这里 我都感觉着平静\n
     * [00:47.100]阳光炽烈 人们慢悠悠的步子\n
     * [00:53.760]零落的草帽 我栽的花儿\n
     * [01:00.500]摇啊摇 摇啊摇 摇啊摇 摇啊摇\n
     * [01:13.760]\n
     * [01:14.180]我在这里一个人唱这首歌\n
     * [01:20.770]人们只是微笑 哦 微笑\n
     * [01:27.510]我在这里一个人唱这首歌\n
     * [01:34.700]你不会知道 哦 知道\n
     * [01:41.600]\n
     * [02:08.529]南部小城 光阴缓流的城\n
     * [02:15.140]每次我回到这里 你都那么的恬静\n
     * [02:22.390]赞美夏天 女孩摇曳的裙摆\n
     * [02:28.839]撩动了昨天 荡着的秋千\n
     * [02:35.549]摇啊摇 摇啊摇 摇啊摇 摇啊摇\n
     * [02:48.859]\n
     * [02:49.219]我在这里一个人唱这首歌\n
     * [02:55.939]人们只是微笑 哦 微笑\n
     * [03:02.769]我在这里一个人唱这首歌\n
     * [03:09.609]你不会知道 哦 知道\n
     * [03:16.249]我在这里一个人唱这首歌\n
     * [03:23.119]人们只是微笑 哦 微笑\n
     * [03:29.859]我在这里一个人唱这首歌\n
     * [03:36.739]你不会知道 哦 知道\n
     * [03:45.289]\n
     * [03:47.659]\n"},
     * "klyric":{"version":3,"lyric":null},"tlyric":{"version":0},"code":200}
     * y
     */
    public GetLyric() {
        mLyricContentList = new ArrayList<>();
        mLyricContent = new LyricContent();
    }


    public void readLyric(final String url, final RequestBody formbody, final Context context, final boolean forceCache) {
        final String[] lrcString = new String[1];

        new Thread() {

            @Override
            public void run() {
                lrcString[0] = HttpUtil.PostResposeJsonObject(url, formbody, context, forceCache).getAsJsonObject("lrc").get("lyric").getAsString();
                Message message = Message.obtain();
                message.obj = lrcString[0];
                handler.sendMessage(message);


            }


        }.start();




    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            formatLrc((String) msg.obj);
            for (int i = 0; i < mLyricContentList.size(); i++) {
                System.out.println(mLyricContentList.get(i).getLyricTime() + " " + mLyricContentList.get(i).getLyricString());
            }
        }
    };
    private void formatLrc(String lrcString) {
        String s = "";
        while (lrcString != null) {
            s = s.replace("[", "");
            s = s.replace("]", "@");

            String splitLrcData[] = s.split("@");
            if (splitLrcData.length > 1) {
                mLyricContent.setLyricString(splitLrcData[1]);

                int lrcTime = time2Str(splitLrcData[0]);
                mLyricContent.setLyricTime(lrcTime);
                mLyricContentList.add(mLyricContent);

                mLyricContent = new LyricContent();
            }

        }

    }

    private int time2Str(String timeStr) {
        timeStr = timeStr.replace(":", ".");
        timeStr = timeStr.replace(".", "@");

        String timeData[] = timeStr.split("@");

        int min = Integer.parseInt(timeData[0]);
        int sec = Integer.parseInt(timeData[1]);
        int millisec = Integer.parseInt(timeData[2]);

        int currentTime = (min * 60 + sec) * 1000 + millisec * 10;
        return currentTime;
    }

    public List<LyricContent> getLyricContentList() {
        return mLyricContentList;
    }


}
