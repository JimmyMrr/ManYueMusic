# 漫悦音乐

## 1 网易云音乐API描述接口[API.java][1]

### 1.1 网易云音乐的层次
- 网易云音乐Fragment [AllPlayListFromNeteaseFrag.java][2]
    - 音乐歌单列表 [NeteasePlayList.java][3]
        - 音乐歌单 
            - 音乐列表 [NeteaseSongList.java][4]
                - 音乐  
                    - 歌词
                        - MP3
   
   
[1]: app/src/main/java/com/music/manyue/API/API.java
[2]: app/src/main/java/com/music/manyue/Fragment/AllPlayListFromNeteaseFrag.java
[3]: app/src/main/java/com/music/manyue/DataItem/NeteasePlayList.java
[4]: app/src/main/java/com/music/manyue/DataItem/NeteaseSongList.java