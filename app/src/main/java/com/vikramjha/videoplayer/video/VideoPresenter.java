package com.vikramjha.videoplayer.video;

import android.app.Activity;
import android.view.View;

import com.vikramjha.videoplayer.VideoApplication;
import com.vikramjha.videoplayer.VideoListApi;
import com.vikramjha.videoplayer.cache.AppCache;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.vikramjha.videoplayer.utils.utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPresenter implements IVideoInterface.Presenter {

    IVideoInterface.View view;
    View rootView;
    VideoListApi videoListApi;

    public VideoPresenter(IVideoInterface.View view) {
        this.view = view;
    }


    @Override
    public void getData(Activity context) {

    }

    @Override
    public void getRelatedVideos(VideoFragment videoFragment, final VideoList video) {
        final ArrayList<VideoList> videoLists = AppCache.getInstance().getVideoLists();
        if (videoLists != null && videoLists.size() > 0) {
            ArrayList<VideoList> relatedList = new ArrayList<>(videoLists);
            relatedList.remove(video);
            view.setAdapterData(relatedList);
        } else {
            videoListApi = utility.getRetrofit(videoFragment.activity).create(VideoListApi.class);
            Call<ArrayList<VideoList>> response = videoListApi.videoListResponse("pretty");
            response.enqueue(new Callback<ArrayList<VideoList>>() {

                @Override
                public void onResponse(Call<ArrayList<VideoList>> call, Response<ArrayList<VideoList>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<VideoList> videos = response.body();
                        AppCache.getInstance().setVideoLists(videos);
                        ArrayList<VideoList> relatedList = new ArrayList<>(videoLists);
                        relatedList.remove(video);
                        view.setAdapterData(relatedList);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ArrayList<VideoList>> call, Throwable t) {
                    view.errorMessage("Data Fetching Error");
                }

            });
        }
    }

    @Override
    public void updateVideoPosition(VideoList video) {
        int count = VideoApplication.getInstance().initDB().videoListDao().countByUrl(video.getUrl());
        if (count == 1) {
            VideoApplication.getInstance().initDB().videoListDao().update(video);
        } else {
            VideoApplication.getInstance().initDB().videoListDao().insert(video);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void setView(View view1) {
        rootView = view1;
        view.initViews(rootView);
    }

    @Override
    public void setActivity(Activity activity) {

    }
}