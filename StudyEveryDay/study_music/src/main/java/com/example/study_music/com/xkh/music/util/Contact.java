package com.example.study_music.com.xkh.music.util;


public class Contact {
    public static final String TING = "https://tingapi.ting.baidu.com/v1/restserver/";
    public static final String CATE = "/sort/sort.php";
    public static final String SIGN_IN = "/login/login_ok.json";


    public static final String RECOMMEND_CACHE = "recommend";
    public static final String ONLINE_MUSIC_CACHE = "online_music";
    public static final String ONLINE_MUSIC_INFO_CACHE = "online_info_music";
    public static final String ONLINE_MUSIC_INFO_USER_CACHE = "online_info_to_user_music";
    public static final String SHEET_INFO = "sheet_info";

    public static final String DOWNLOAD_MUSIC_INFO_CACHE = "download_info_music";


    public static final String OFFSET_LOCATION_LAST_LOAD = "last_load";


    public static final int INDEX_CACHE_TIME = 12 * 60;
    public static final int LOCAL_MUSIC_CACHE_TIME = 24 * 60 * 60;
    public static final int MUSIC_DOWNLOAD_CACHE_TIME = 24 * 60 * 60 * 30;


    public static final String METHOD_GET_MUSIC_LIST = "baidu.ting.billboard.billList";
    public static final String METHOD_DOWNLOAD_MUSIC = "baidu.ting.song.play";
    public static final String METHOD_ARTIST_INFO = "baidu.ting.artist.getInfo";
    public static final String METHOD_SEARCH_MUSIC = "baidu.ting.search.catalogSug";
    public static final String METHOD_LRC = "baidu.ting.song.lry";
    public static final String METHOD_ALBUM_INFO = "baidu.ting.album.getAlbumInfo";

    public static final String PARAM_VERSION = "version";
    public static final String PARAM_ALBUM_ID = "album_id";
    public static final String PARAM_FORMAT = "json";
    public static final String VERSION = "5.6.5.0";


    public static final String PARAM_METHOD = "method";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_OFFSET = "offset";
    public static final String PARAM_SONG_ID = "songid";
    public static final String PARAM_TING_UID = "tinguid";
    public static final String PARAM_QUERY = "query";

    public static final String LOCAL_MUSIC = "localMusic";

    public static final int SHUFFLE = 0;
    public static final int SINGLE = 1;
    public static final int LOOP = 2;

    public static final String MUSIC_ACTIVITY = "音乐活动";
    public static final String FU_FEI_ALBUM = "付费专辑";
    public static final String MUSIC_WALL = "唱片墙";


}
