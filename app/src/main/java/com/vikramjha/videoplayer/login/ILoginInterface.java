package com.vikramjha.videoplayer.login;

import android.app.Activity;

import com.vikramjha.videoplayer.base.BasePresenter;
import com.vikramjha.videoplayer.base.BaseView;
import com.vikramjha.videoplayer.home.IHomeInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface ILoginInterface {
    interface View extends BaseView<IHomeInterface.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void getData(Activity context);

        void validateAccount(GoogleSignInAccount account);
    }
}
