package com.vikramjha.videoplayer.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class DialogUtils {
    public static ProgressDialog dialog;

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showProgress(Activity context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading..");
        dialog.show();
    }

    public static void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
