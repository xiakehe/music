package com.example.study_music.com.xkh.music.main.index.bean;


import java.io.Serializable;

public class SheetInfo  implements Serializable {
    private String title;
    private String type;
    private String url;
    private String musicOne;
    private String musicOneAu;

    private String musicTwoAu;
    private String musicTwo;
    private String musicThree;
    private String musicThreeAu;
    private String update;
    private String info;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMusicOne() {
        return musicOne;
    }

    public void setMusicOne(String musicOne) {
        this.musicOne = musicOne;
    }

    public String getMusicOneAu() {
        return musicOneAu;
    }

    public void setMusicOneAu(String musicOneAu) {
        this.musicOneAu = musicOneAu;
    }

    public String getMusicTwoAu() {
        return musicTwoAu;
    }

    public void setMusicTwoAu(String musicTwoAu) {
        this.musicTwoAu = musicTwoAu;
    }

    public String getMusicTwo() {
        return musicTwo;
    }

    public void setMusicTwo(String musicTwo) {
        this.musicTwo = musicTwo;
    }

    public String getMusicThree() {
        return musicThree;
    }

    public void setMusicThree(String musicThree) {
        this.musicThree = musicThree;
    }

    public String getMusicThreeAu() {
        return musicThreeAu;
    }

    public void setMusicThreeAu(String musicThreeAu) {
        this.musicThreeAu = musicThreeAu;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "SheetInfo{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", musicOne='" + musicOne + '\'' +
                ", musicTwo='" + musicTwo + '\'' +
                ", musicThree='" + musicThree + '\'' +
                '}';
    }
}
