package com.vikramjha.videoplayer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.vikramjha.videoplayer.base.BaseActivity;
import com.vikramjha.videoplayer.home.HomeFragment;
import com.vikramjha.videoplayer.listener.onFragmentIntraction;
import com.vikramjha.videoplayer.login.LoginFragment;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.vikramjha.videoplayer.utils.AppConstant;
import com.vikramjha.videoplayer.video.VideoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vikramjha.videoplayer.R;

public class MainActivity extends BaseActivity implements onFragmentIntraction {

    private FragmentManager mFragmentManager;
    private String mFragmentTag;
    private int mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
            onFragmentInteraction(AppConstant.LOGIN_FRAGMENT, null);
        else
            onFragmentInteraction(AppConstant.HOME_FRAGMENT, null);
    }

    @Override
    public void onFragmentInteraction(int fragmentId, Object data) {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = fragmentId;
        mFragmentTag = String.valueOf(fragmentId);

        switch (fragmentId) {
            case AppConstant.HOME_FRAGMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, HomeFragment.newInstance(), mFragmentTag).commit();
                break;
            case AppConstant.VIDEO_FRAGMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, VideoFragment.getInstance((VideoList) data), mFragmentTag).commit();
                break;
            case AppConstant.LOGIN_FRAGMENT:
                mFragmentManager.beginTransaction().addToBackStack(mFragmentTag).replace(R.id.fragment_main, new LoginFragment(), mFragmentTag).commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
       /* if(isPopupShown) {
            hidePopupWindow();
        } else*/
        if (count <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentUpdate(int type, Object data) {
        switch (type) {
            case AppConstant.GOTO_HOME:
                gotoHomePage();
                break;
        }
    }

    public void gotoHomePage() {
        try {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            onFragmentInteraction(AppConstant.HOME_FRAGMENT, false);
        } catch (Exception e) {
        }
    }

    @Override
    public void onInitFragment(int fragmentId, String title) {

    }
}
