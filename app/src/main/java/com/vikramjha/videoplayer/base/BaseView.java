package com.vikramjha.videoplayer.base;

import android.view.View;

public interface BaseView<T> {

    void initViews(View view);

    void successResult();

    void failureResult();

    void errorMessage(String data_fetching_error);
}
