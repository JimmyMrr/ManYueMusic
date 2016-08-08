package com.example.ritchie_huang.manyuemusic.Util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.ritchie_huang.manyuemusic.DataItem.MP3InfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritchie-huang on 16-8-4.
 */
public class LocalSongs {

    private List<MP3InfoItem> mp3InfoItemList;
    public List<MP3InfoItem> getMp3Infos(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        mp3InfoItemList = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++) {
            MP3InfoItem mp3InfoItem = new MP3InfoItem();                               //新建一个歌曲对象,将从cursor里读出的信息存放进去,直到取完cursor里面的内容为止.
            cursor.moveToNext();
            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));   //音乐id

            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题

            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长

            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片

            long album_id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID

            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐

            if (isMusic != 0 && duration/(1000 * 60) >= 1) {     //只把1分钟以上的音乐添加到集合当中
                mp3InfoItem.setId(id);
                mp3InfoItem.setTitle(title);
                mp3InfoItem.setArtist(artist);
                mp3InfoItem.setDuration(duration);
                mp3InfoItem.setSize(size);
                mp3InfoItem.setUrl(url);
                mp3InfoItem.setAlbum(album);
                mp3InfoItem.setAlbum_id(album_id);
                mp3InfoItemList.add(mp3InfoItem);
            }
        }
        return mp3InfoItemList;
    }
}
