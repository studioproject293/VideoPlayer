package com.vikramjha.videoplayer.listener;

public interface onFragmentIntraction {

    void onFragmentInteraction(int fragmentId, Object data);
    void onFragmentUpdate(int type, Object data);
    void onInitFragment(int fragmentId, String title);

}
