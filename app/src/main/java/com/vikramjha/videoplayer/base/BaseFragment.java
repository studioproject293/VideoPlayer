package com.vikramjha.videoplayer.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vikramjha.videoplayer.listener.onFragmentIntraction;


public class BaseFragment extends Fragment {
    Activity activity;
    public onFragmentIntraction mListener;
    protected AppCompatActivity mActivity;
    int fragmentId;
    String screenName;

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onViewCreated(view, savedInstanceState);
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setScreenName(int id, String title) {
        this.screenName = title;
        this.fragmentId = id;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //      // QLog.m11v("Class:" + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onAttach(Activity activity) {
        //    // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
        this.activity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        //  // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onCreate(savedInstanceState);
    }

    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        //// // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onInflate(activity, attrs, savedInstanceState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStart();
    }

    public void onResume() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onResume();
        mListener.onInitFragment(fragmentId, screenName);
    }

    public void onPause() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onPause();
    }

    public void onStop() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStop();
    }

    public void onDestroyView() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroyView();
    }

    public void onDestroy() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroy();
    }


    public void onLowMemory() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        ((MainActivity)getActivity()).setEventListner((EventListner)context);
        if (context instanceof onFragmentIntraction) {
            mListener = (onFragmentIntraction) context;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mActivity = null;
    }

    public AppCompatActivity getmActivity() {
        return mActivity;
    }

    public void permissionGranted(int mode) {

    }

    public void permissionDenied(int mode) {

    }
}
