package com.vikramjha.videoplayer.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.vikramjha.videoplayer.R;
import com.vikramjha.videoplayer.base.BaseFragment;
import com.vikramjha.videoplayer.listener.OnFragmentListItemSelectListener;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.vikramjha.videoplayer.utils.AppConstant;
import com.vikramjha.videoplayer.utils.DialogUtils;


import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements IHomeInterface.View, OnFragmentListItemSelectListener {
    View rootView;
    Activity activity;
    IHomeInterface.Presenter presenter;
    RecyclerView recyclerView;
    VideoListAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.video_list, container, false);
        activity = getActivity();
        presenter = new HomePresenter(this);
        DialogUtils.showProgress(activity);
        presenter.setView(rootView);
        return rootView;
    }

    @Override
    public void setAdapterData(ArrayList<VideoList> videoLists) {
        DialogUtils.hideProgress();
        if (videoLists != null && videoLists.size() > 0) {
            if (adapter == null)
                adapter = new VideoListAdapter(activity, videoLists);
            else
                adapter.updateVideoList(videoLists);
            adapter.setListner(this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(activity);
    }

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.videoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void successResult() {

    }

    @Override
    public void failureResult() {

    }

    @Override
    public void errorMessage(String data_fetching_error) {
        DialogUtils.showToast(activity, data_fetching_error);
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        switch (itemId) {
            case R.id.video_row:
                VideoList list = (VideoList) data;
                mListener.onFragmentInteraction(AppConstant.VIDEO_FRAGMENT, list);
                break;
        }
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }
}
