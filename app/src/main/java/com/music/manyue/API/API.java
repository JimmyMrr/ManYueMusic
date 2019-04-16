package com.music.manyue.API;

/**
 * Created by ritchie-huang on 16-8-6.
 */
public class API {


    //get请求
    public static String GEDAN = "http://music.163.com/api/playlist/list?cat=全部&order=hot&offset=0&total=true&limit=1470";
    //http://music.163.com/api/playlist/detail?id=xxx;
    public static String GEDAN_DETAIL = "http://music.163.com/api/playlist/detail?id=";
    //http://music.163.com/api/song/detail/?id=xxx&ids=[xxx];
    public static String SONG_DETAIL_URL = "http://music.163.com/api/song/detail/?id=";
    //http://music.163.com/api/song/lyric?os=osx&id=xxx&lv=-1&kv=-1&tv=-1;
    public static String SONG_LRC = "http://music.163.com/api/song/lyric?os=osx";
    //https://music.163.com/song/media/outer/url?id=[xxx].mp3
    public static String SONG_MP3_URL = "https://music.163.com/song/media/outer/url?id=";


    //post请求
    public static String SEARCH = "http://music.163.com/api/search/get";
//    data = {
//            's': s,
//            'type': stype,搜索单曲(1)，歌手(100)，专辑(10)，歌单(1000)，用户(1002) *(type)*
//            'offset': offset,
//            'total': total,
//            'limit': 60
//   }


}
