package com.example.study_music.com.xkh.music.main.index.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * JavaBean
 * Created by wcy on 2015/12/20.
 */
public class OnlineMusicList implements Serializable {
    @SerializedName("song_list")
    private List<OnlineMusic> song_list;
    @SerializedName("billboard")
    private Billboard billboard;

    @SerializedName("error_code")
    private int error_code;

    public int getError_code() {
        return error_code;
    }

    @Override
    public String toString() {
        return "OnlineMusicList{" +
                "song_list=" + song_list +
                ", billboard=" + billboard +
                ", error_code=" + error_code +
                '}';
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<OnlineMusic> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<OnlineMusic> song_list) {
        this.song_list = song_list;
    }

    public Billboard getBillboard() {
        return billboard;
    }

    public void setBillboard(Billboard billboard) {
        this.billboard = billboard;
    }


    public static class OnlineMusic {


        @SerializedName("artist_id")
        private String artist_id;
        @SerializedName("language")
        private String language;
        @SerializedName("pic_big")
        private String pic_big;
        @SerializedName("pic_small")
        private String pic_small;
        @SerializedName("country")
        private String country;
        @SerializedName("area")
        private String area;
        @SerializedName("publishtime")
        private String publishtime;
        @SerializedName("album_no")
        private String album_no;
        @SerializedName("lrclink")
        private String lrclink;
        @SerializedName("copy_type")
        private String copy_type;
        @SerializedName("hot")
        private String hot;
        @SerializedName("all_artist_ting_uid")
        private String all_artist_ting_uid;
        @SerializedName("resource_type")
        private String resource_type;
        @SerializedName("is_new")
        private String is_new;
        @SerializedName("rank_change")
        private String rank_change;
        @SerializedName("rank")
        private String rank;
        @SerializedName("all_artist_id")
        private String all_artist_id;
        @SerializedName("style")
        private String style;
        @SerializedName("del_status")
        private String del_status;
        @SerializedName("relate_status")
        private String relate_status;
        @SerializedName("toneid")
        private String toneid;
        @SerializedName("all_rate")
        private String all_rate;
        @SerializedName("file_duration")
        private int file_duration;
        @SerializedName("has_mv_mobile")
        private int has_mv_mobile;
        @SerializedName("versions")
        private String versions;
        @SerializedName("bitrate_fee")
        private String bitrate_fee;
        @SerializedName("biaoshi")
        private String biaoshi;
        @SerializedName("info")
        private String info;
        @SerializedName("has_filmtv")
        private String has_filmtv;
        @SerializedName("si_proxycompany")
        private String si_proxycompany;
        @SerializedName("res_encryption_flag")
        private String res_encryption_flag;
        @SerializedName("song_id")
        private String song_id;
        @SerializedName("title")
        private String title;
        @SerializedName("ting_uid")
        private String ting_uid;
        @SerializedName("author")
        private String author;
        @SerializedName("album_id")
        private String album_id;
        @SerializedName("album_title")
        private String album_title;
        @SerializedName("is_first_publish")
        private int is_first_publish;
        @SerializedName("havehigh")
        private int havehigh;
        @SerializedName("charge")
        private int charge;
        @SerializedName("has_mv")
        private int has_mv;
        @SerializedName("learn")
        private int learn;
        @SerializedName("song_source")
        private String song_source;
        @SerializedName("piao_id")
        private String piao_id;
        @SerializedName("korean_bb_song")
        private String korean_bb_song;
        @SerializedName("resource_type_ext")
        private String resource_type_ext;
        @SerializedName("mv_provider")
        private String mv_provider;
        @SerializedName("artist_name")
        private String artist_name;
        @SerializedName("pic_radio")
        private String pic_radio;
        @SerializedName("pic_s500")
        private String pic_s500;
        @SerializedName("pic_premium")
        private String pic_premium;
        @SerializedName("pic_huge")
        private String pic_huge;
        @SerializedName("album_500_500")
        private String album_500_500;
        @SerializedName("album_800_800")
        private String album_800_800;
        @SerializedName("album_1000_1000")
        private String album_1000_1000;

        @Override
        public String toString() {
            return "OnlineMusic{" +
                    "artist_id='" + artist_id + '\'' +
                    ", language='" + language + '\'' +
                    ", pic_big='" + pic_big + '\'' +
                    ", pic_small='" + pic_small + '\'' +
                    ", country='" + country + '\'' +
                    ", area='" + area + '\'' +
                    ", publishtime='" + publishtime + '\'' +
                    ", album_no='" + album_no + '\'' +
                    ", lrclink='" + lrclink + '\'' +
                    ", copy_type='" + copy_type + '\'' +
                    ", hot='" + hot + '\'' +
                    ", all_artist_ting_uid='" + all_artist_ting_uid + '\'' +
                    ", resource_type='" + resource_type + '\'' +
                    ", is_new='" + is_new + '\'' +
                    ", rank_change='" + rank_change + '\'' +
                    ", rank='" + rank + '\'' +
                    ", all_artist_id='" + all_artist_id + '\'' +
                    ", style='" + style + '\'' +
                    ", del_status='" + del_status + '\'' +
                    ", relate_status='" + relate_status + '\'' +
                    ", toneid='" + toneid + '\'' +
                    ", all_rate='" + all_rate + '\'' +
                    ", file_duration=" + file_duration +
                    ", has_mv_mobile=" + has_mv_mobile +
                    ", versions='" + versions + '\'' +
                    ", bitrate_fee='" + bitrate_fee + '\'' +
                    ", biaoshi='" + biaoshi + '\'' +
                    ", info='" + info + '\'' +
                    ", has_filmtv='" + has_filmtv + '\'' +
                    ", si_proxycompany='" + si_proxycompany + '\'' +
                    ", res_encryption_flag='" + res_encryption_flag + '\'' +
                    ", song_id='" + song_id + '\'' +
                    ", title='" + title + '\'' +
                    ", ting_uid='" + ting_uid + '\'' +
                    ", author='" + author + '\'' +
                    ", album_id='" + album_id + '\'' +
                    ", album_title='" + album_title + '\'' +
                    ", is_first_publish=" + is_first_publish +
                    ", havehigh=" + havehigh +
                    ", charge=" + charge +
                    ", has_mv=" + has_mv +
                    ", learn=" + learn +
                    ", song_source='" + song_source + '\'' +
                    ", piao_id='" + piao_id + '\'' +
                    ", korean_bb_song='" + korean_bb_song + '\'' +
                    ", resource_type_ext='" + resource_type_ext + '\'' +
                    ", mv_provider='" + mv_provider + '\'' +
                    ", artist_name='" + artist_name + '\'' +
                    ", pic_radio='" + pic_radio + '\'' +
                    ", pic_s500='" + pic_s500 + '\'' +
                    ", pic_premium='" + pic_premium + '\'' +
                    ", pic_huge='" + pic_huge + '\'' +
                    ", album_500_500='" + album_500_500 + '\'' +
                    ", album_800_800='" + album_800_800 + '\'' +
                    ", album_1000_1000='" + album_1000_1000 + '\'' +
                    '}';
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry() {
            return country;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea() {
            return area;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setAlbum_no(String album_no) {
            this.album_no = album_no;
        }

        public String getAlbum_no() {
            return album_no;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getHot() {
            return hot;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setRank_change(String rank_change) {
            this.rank_change = rank_change;
        }

        public String getRank_change() {
            return rank_change;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getRank() {
            return rank;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getStyle() {
            return style;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }

        public String getDel_status() {
            return del_status;
        }

        public void setRelate_status(String relate_status) {
            this.relate_status = relate_status;
        }

        public String getRelate_status() {
            return relate_status;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getToneid() {
            return toneid;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public int getFile_duration() {
            return file_duration;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getVersions() {
            return versions;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBiaoshi(String biaoshi) {
            this.biaoshi = biaoshi;
        }

        public String getBiaoshi() {
            return biaoshi;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        public void setHas_filmtv(String has_filmtv) {
            this.has_filmtv = has_filmtv;
        }

        public String getHas_filmtv() {
            return has_filmtv;
        }

        public void setSi_proxycompany(String si_proxycompany) {
            this.si_proxycompany = si_proxycompany;
        }

        public String getSi_proxycompany() {
            return si_proxycompany;
        }

        public void setRes_encryption_flag(String res_encryption_flag) {
            this.res_encryption_flag = res_encryption_flag;
        }

        public String getRes_encryption_flag() {
            return res_encryption_flag;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return author;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setIs_first_publish(int is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public int getIs_first_publish() {
            return is_first_publish;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getCharge() {
            return charge;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public int getLearn() {
            return learn;
        }

        public void setSong_source(String song_source) {
            this.song_source = song_source;
        }

        public String getSong_source() {
            return song_source;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setKorean_bb_song(String korean_bb_song) {
            this.korean_bb_song = korean_bb_song;
        }

        public String getKorean_bb_song() {
            return korean_bb_song;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setMv_provider(String mv_provider) {
            this.mv_provider = mv_provider;
        }

        public String getMv_provider() {
            return mv_provider;
        }

        public void setArtist_name(String artist_name) {
            this.artist_name = artist_name;
        }

        public String getArtist_name() {
            return artist_name;
        }

        public void setPic_radio(String pic_radio) {
            this.pic_radio = pic_radio;
        }

        public String getPic_radio() {
            return pic_radio;
        }

        public void setPic_s500(String pic_s500) {
            this.pic_s500 = pic_s500;
        }

        public String getPic_s500() {
            return pic_s500;
        }

        public void setPic_premium(String pic_premium) {
            this.pic_premium = pic_premium;
        }

        public String getPic_premium() {
            return pic_premium;
        }

        public void setPic_huge(String pic_huge) {
            this.pic_huge = pic_huge;
        }

        public String getPic_huge() {
            return pic_huge;
        }

        public void setAlbum_500_500(String album_500_500) {
            this.album_500_500 = album_500_500;
        }

        public String getAlbum_500_500() {
            return album_500_500;
        }

        public void setAlbum_800_800(String album_800_800) {
            this.album_800_800 = album_800_800;
        }

        public String getAlbum_800_800() {
            return album_800_800;
        }

        public void setAlbum_1000_1000(String album_1000_1000) {
            this.album_1000_1000 = album_1000_1000;
        }

        public String getAlbum_1000_1000() {
            return album_1000_1000;
        }

    }

    public static class Billboard {
        @SerializedName("billboard_type")
        private String billboard_type;
        @SerializedName("billboard_no")
        private String billboard_no;
        @SerializedName("billboard_songnum")
        private String billboard_songnum;
        @SerializedName("havemore")
        private int havemore;

        @SerializedName("update_date")
        private String update_date;
        @SerializedName("name")
        private String name;
        @SerializedName("comment")
        private String comment;
        @SerializedName("pic_s640")
        private String pic_s640;
        @SerializedName("pic_s444")
        private String pic_s444;
        @SerializedName("pic_s192")
        private String pic_s192;
        @SerializedName("web_url")

        private String web_url;

        @SerializedName("pic_s260")
        private String pic_s260;
        @SerializedName("pic_s210")
        private String pic_s210;

        @Override
        public String toString() {
            return "Billboard{" +
                    "billboard_type='" + billboard_type + '\'' +
                    ", billboard_no='" + billboard_no + '\'' +
                    ", billboard_songnum='" + billboard_songnum + '\'' +
                    ", havemore=" + havemore +
                    ", update_date='" + update_date + '\'' +
                    ", name='" + name + '\'' +
                    ", comment='" + comment + '\'' +
                    ", pic_s640='" + pic_s640 + '\'' +
                    ", pic_s444='" + pic_s444 + '\'' +
                    ", pic_s192='" + pic_s192 + '\'' +
                    ", web_url='" + web_url + '\'' +
                    ", pic_s260='" + pic_s260 + '\'' +
                    ", pic_s210='" + pic_s210 + '\'' +
                    '}';
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPic_s640() {
            return pic_s640;
        }

        public void setPic_s640(String pic_s640) {
            this.pic_s640 = pic_s640;
        }

        public String getPic_s444() {
            return pic_s444;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public String getPic_s260() {
            return pic_s260;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public String getPic_s210() {
            return pic_s210;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }


    }
}
