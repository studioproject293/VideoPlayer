package com.vikramjha.videoplayer;

import com.vikramjha.videoplayer.pojo.VideoList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoListApi {
    @GET("media.json")
    Call<ArrayList<VideoList>> videoListResponse(@Query("print") String print);

}
