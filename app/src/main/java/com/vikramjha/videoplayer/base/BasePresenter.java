package com.vikramjha.videoplayer.base;

import android.app.Activity;
import android.view.View;

public interface BasePresenter {
    void start();

    void setView(View view);

    void setActivity(Activity activity);
}
