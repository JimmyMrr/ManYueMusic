package com.example.ritchie_huang.manyuemusic.DataItem;

import java.util.List;

/**
 * Created by ritchie-huang on 16-8-6.
 */
public class GedanListNeteaseItem {

    /**
     * playlists : [{"subscribers":[],"subscribed":false,"creator":{"userId":64631536,"nickname":"时光如水你好","avatarUrl":"http://p4.music.126.net/rhzpxazsxaAXWWMk6lw-gQ==/1364493959369212.jpg","expertTags":["流行","华语","欧美"],"remarkName":null,"mutual":false,"defaultAvatar":false,"gender":2,"birthday":800985600000,"city":1002900,"backgroundImgId":1406275375761547,"avatarImgId":1364493959369212,"province":1000000,"vipType":0,"userType":0,"description":"","followed":false,"backgroundUrl":"http://p1.music.126.net/GunZRAYL69oj48Vx_vWnpg==/1406275375761547.jpg","accountStatus":0,"authStatus":0,"detailDescription":"","djStatus":10,"signature":"喜欢音乐，热爱歌词的制作和创作，希望能够结交和我兴趣一样的朋友","authority":0},"artists":null,"tracks":null,"status":0,"userId":64631536,"updateTime":1469033648079,"playCount":44506,"trackCount":22,"coverImgUrl":"http://p4.music.126.net/9P8Xs-ukLD1coCzLvcS2Iw==/3444769936647854.jpg","highQuality":false,"coverImgId":3444769936647854,"createTime":1469029056260,"specialType":0,"commentThreadId":"A_PL_0_426782554","tags":["华语","流行","旅行"],"subscribedCount":374,"trackNumberUpdateTime":1469033648079,"totalDuration":0,"adType":0,"description":"对于身处浩瀚宇宙中，一颗渺小蓝色星球上，如同尘埃一样微不足道的我们，在这个狭小的世界上旅行有什么意义呢？\n这个世界上，没有任何东西的美感，可以足以代替自然之美。所以，我想打破生活的平静，寻找另一番景致，每一种景致都足以触发新的人生。世上有不绝的风景，我们都要有不老的心情。","privacy":0,"cloudTrackCount":0,"trackUpdateTime":1469058776650,"newImported":false,"name":"世界上有不绝的风景，我有不老的心情","id":426782554,"shareCount":7,"commentCount":12},
     * code : 200
     * more : true
     * total : 1467
     */

    private int total;
    /**
     * subscribers : []
     * subscribed : false
     * creator : {"userId":64631536,"nickname":"时光如水你好","avatarUrl":"http://p4.music.126.net/rhzpxazsxaAXWWMk6lw-gQ==/1364493959369212.jpg","expertTags":["流行","华语","欧美"],"remarkName":null,"mutual":false,"defaultAvatar":false,"gender":2,"birthday":800985600000,"city":1002900,"backgroundImgId":1406275375761547,"avatarImgId":1364493959369212,"province":1000000,"vipType":0,"userType":0,"description":"","followed":false,"backgroundUrl":"http://p1.music.126.net/GunZRAYL69oj48Vx_vWnpg==/1406275375761547.jpg","accountStatus":0,"authStatus":0,"detailDescription":"","djStatus":10,"signature":"喜欢音乐，热爱歌词的制作和创作，希望能够结交和我兴趣一样的朋友","authority":0}
     * artists : null
     * tracks : null
     * status : 0
     * userId : 64631536
     * updateTime : 1469033648079
     * playCount : 44506
     * trackCount : 22
     * coverImgUrl : http://p4.music.126.net/9P8Xs-ukLD1coCzLvcS2Iw==/3444769936647854.jpg
     * highQuality : false
     * coverImgId : 3444769936647854
     * createTime : 1469029056260
     * specialType : 0
     * commentThreadId : A_PL_0_426782554
     * tags : ["华语","流行","旅行"]
     * subscribedCount : 374
     * trackNumberUpdateTime : 1469033648079
     * totalDuration : 0
     * adType : 0
     * description : 对于身处浩瀚宇宙中，一颗渺小蓝色星球上，如同尘埃一样微不足道的我们，在这个狭小的世界上旅行有什么意义呢？
     这个世界上，没有任何东西的美感，可以足以代替自然之美。所以，我想打破生活的平静，寻找另一番景致，每一种景致都足以触发新的人生。世上有不绝的风景，我们都要有不老的心情。
     * privacy : 0
     * cloudTrackCount : 0
     * trackUpdateTime : 1469058776650
     * newImported : false
     * name : 世界上有不绝的风景，我有不老的心情
     * id : 426782554
     * shareCount : 7
     * commentCount : 12
     */


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


        private boolean subscribed;
        /**
         * userId : 64631536
         * nickname : 时光如水你好
         * avatarUrl : http://p4.music.126.net/rhzpxazsxaAXWWMk6lw-gQ==/1364493959369212.jpg
         * expertTags : ["流行","华语","欧美"]
         * remarkName : null
         * mutual : false
         * defaultAvatar : false
         * gender : 2
         * birthday : 800985600000
         * city : 1002900
         * backgroundImgId : 1406275375761547
         * avatarImgId : 1364493959369212
         * province : 1000000
         * vipType : 0
         * userType : 0
         * description :
         * followed : false
         * backgroundUrl : http://p1.music.126.net/GunZRAYL69oj48Vx_vWnpg==/1406275375761547.jpg
         * accountStatus : 0
         * authStatus : 0
         * detailDescription :
         * djStatus : 10
         * signature : 喜欢音乐，热爱歌词的制作和创作，希望能够结交和我兴趣一样的朋友
         * authority : 0
         */

        private CreatorBean creator;
        private int userId;
        private int playCount;
        private int trackCount;
        private String coverImgUrl;
        private long coverImgId;
        private long createTime;
        private int specialType;
        private String commentThreadId;
        private int subscribedCount;
        private long trackNumberUpdateTime;
        private int totalDuration;
        private int adType;
        private String description;
        private int cloudTrackCount;
        private String name;
        private int id;
        private List<?> subscribers;
        private List<String> tags;

        public boolean isSubscribed() {
            return subscribed;
        }

        public void setSubscribed(boolean subscribed) {
            this.subscribed = subscribed;
        }

        public CreatorBean getCreator() {
            return creator;
        }

        public void setCreator(CreatorBean creator) {
            this.creator = creator;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public int getTrackCount() {
            return trackCount;
        }

        public void setTrackCount(int trackCount) {
            this.trackCount = trackCount;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public long getCoverImgId() {
            return coverImgId;
        }

        public void setCoverImgId(long coverImgId) {
            this.coverImgId = coverImgId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getSpecialType() {
            return specialType;
        }

        public void setSpecialType(int specialType) {
            this.specialType = specialType;
        }

        public String getCommentThreadId() {
            return commentThreadId;
        }

        public void setCommentThreadId(String commentThreadId) {
            this.commentThreadId = commentThreadId;
        }

        public int getSubscribedCount() {
            return subscribedCount;
        }

        public void setSubscribedCount(int subscribedCount) {
            this.subscribedCount = subscribedCount;
        }

        public long getTrackNumberUpdateTime() {
            return trackNumberUpdateTime;
        }

        public void setTrackNumberUpdateTime(long trackNumberUpdateTime) {
            this.trackNumberUpdateTime = trackNumberUpdateTime;
        }

        public int getTotalDuration() {
            return totalDuration;
        }

        public void setTotalDuration(int totalDuration) {
            this.totalDuration = totalDuration;
        }

        public int getAdType() {
            return adType;
        }

        public void setAdType(int adType) {
            this.adType = adType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCloudTrackCount() {
            return cloudTrackCount;
        }

        public void setCloudTrackCount(int cloudTrackCount) {
            this.cloudTrackCount = cloudTrackCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<?> getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(List<?> subscribers) {
            this.subscribers = subscribers;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public static class CreatorBean {
            private int userId;
            private String nickname;
            private String avatarUrl;
            private long backgroundImgId;
            private long avatarImgId;
            private String description;
            private String backgroundUrl;
            private int djStatus;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public long getBackgroundImgId() {
                return backgroundImgId;
            }

            public void setBackgroundImgId(long backgroundImgId) {
                this.backgroundImgId = backgroundImgId;
            }

            public long getAvatarImgId() {
                return avatarImgId;
            }

            public void setAvatarImgId(long avatarImgId) {
                this.avatarImgId = avatarImgId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public void setBackgroundUrl(String backgroundUrl) {
                this.backgroundUrl = backgroundUrl;
            }

            public int getDjStatus() {
                return djStatus;
            }

            public void setDjStatus(int djStatus) {
                this.djStatus = djStatus;
            }
        }
}
