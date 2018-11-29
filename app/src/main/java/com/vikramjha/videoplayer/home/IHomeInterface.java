package com.vikramjha.videoplayer.home;

import android.app.Activity;

import com.vikramjha.videoplayer.base.BasePresenter;
import com.vikramjha.videoplayer.base.BaseView;
import com.vikramjha.videoplayer.pojo.VideoList;

import java.util.ArrayList;

public interface IHomeInterface {
    interface View extends BaseView<Presenter> {
        void setAdapterData(ArrayList<VideoList> videoLists);
    }

    interface Presenter extends BasePresenter {
        void getData(Activity context);
        void getLoginData(String userName,String Password);
    }
}
