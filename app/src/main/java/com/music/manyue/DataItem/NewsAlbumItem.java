package com.music.manyue.DataItem;

/**
 * Created by ritchie-huang on 16-8-8.
 */
public class NewsAlbumItem {
    public  String coverImgUrl;
    public final long id;
    public final String albumName;
    public final String artistName;
    public int publishTime;

    public NewsAlbumItem() {
        this.coverImgUrl = "";
        this.id = -1;
        this.albumName = "";
        this.artistName = "";
        this.publishTime = -1;

    }

    public NewsAlbumItem(String _coverImg, long _id, String _album_Name,String _artistName,
                     int _publishTime) {
        this.coverImgUrl = _coverImg;
        this.id = _id;
        this.albumName = _album_Name;
        this.artistName = _artistName;
        this.publishTime = _publishTime;
    }
}
