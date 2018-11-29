package com.vikramjha.videoplayer.cache;

import com.vikramjha.videoplayer.pojo.VideoList;

import java.util.ArrayList;

public class AppCache {

    static AppCache appCache;

    public static AppCache getInstance() {
        if (appCache == null)
            appCache = new AppCache();
        return appCache;
    }

    ArrayList<VideoList> videoLists;

    public ArrayList<VideoList> getVideoLists() {
        return videoLists;
    }

    public void setVideoLists(ArrayList<VideoList> videoLists) {
        this.videoLists = videoLists;
    }
}
