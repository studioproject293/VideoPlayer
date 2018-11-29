package com.vikramjha.videoplayer;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.vikramjha.videoplayer.pojo.SampleDatabase;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class VideoApplication extends Application {
    public static VideoApplication instance;
    SampleDatabase sampleDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(60 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        sampleDatabase = Room.databaseBuilder(this, SampleDatabase.class, "videoList").allowMainThreadQueries().build();

        instance = this;
    }

    public SampleDatabase initDB() {

        return sampleDatabase;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    public static VideoApplication getInstance() {
        return instance;
    }

}
