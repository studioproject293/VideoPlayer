package com.vikramjha.videoplayer.video;

import android.app.Activity;

import com.vikramjha.videoplayer.base.BasePresenter;
import com.vikramjha.videoplayer.base.BaseView;
import com.vikramjha.videoplayer.pojo.VideoList;

import java.util.ArrayList;

public interface IVideoInterface {
    interface View extends BaseView<IVideoInterface.Presenter> {
        void setAdapterData(ArrayList<VideoList> videoLists);
    }

    interface Presenter extends BasePresenter {
        void getData(Activity context);

        void getRelatedVideos(VideoList video);

        void updateVideoPosition(VideoList list);
    }
}
