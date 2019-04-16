package com.music.manyue.Util;

/**
 * Created by ritchie-huang on 16-8-26.
 */
public class SongUtil {

    public static String formatDuration(int duration) {
        String min = duration / (1000 * 60) + "";
        String second = duration % (1000 * 60) + "";

        if (min.length() < 2) {
            min = "0" + duration / (1000 * 60) + "";
        } else {
            min = duration / (1000 * 60)+"";
        }

        if (second.length() == 4) {
            second = "0" + (duration % (1000 * 60)) + "";

        } else if (second.length() == 3) {
            second = "00" + (duration % (1000 * 60)) + "";
        } else if (second.length() == 2) {
            second = "000" + (duration % (1000 * 60)) + "";
        } else if (second.length() == 1) {
            second = "0000" + (duration % (1000 * 60)) + "";
        }

        return min + ":" + second.trim().substring(0, 2);

    }
}
