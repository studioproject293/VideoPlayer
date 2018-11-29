package com.vikramjha.videoplayer.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vikramjha.videoplayer.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class utility {

    public static void displayImage(Context context, String url, ImageView view) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(R.drawable.default_img);
            return;
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.default_img)
                .showImageOnFail(R.drawable.default_img)
                .showImageOnLoading(R.drawable.default_img)
                .build();
        imageLoader.displayImage(url, view, options);
    }
    public static Retrofit getRetrofit(Context context) {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3600, TimeUnit.SECONDS)
                .readTimeout(3600, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://interview-e18de.firebaseio.com/").client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
