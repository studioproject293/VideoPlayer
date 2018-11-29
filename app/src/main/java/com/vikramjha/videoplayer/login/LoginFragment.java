package com.vikramjha.videoplayer.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.vikramjha.videoplayer.base.BaseFragment;
import com.vikramjha.videoplayer.utils.AppConstant;
import com.vikramjha.videoplayer.utils.DialogUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.vikramjha.videoplayer.R;

import static android.support.constraint.Constraints.TAG;

public class LoginFragment extends BaseFragment implements ILoginInterface.View, View.OnClickListener {
    View rootView;
    ILoginInterface.Presenter presenter;
    Activity activity;
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login, container, false);
        activity = getActivity();
        presenter = new LoginPresenter(this);
        presenter.setView(rootView);
        presenter.setActivity(activity);
        return rootView;
    }

    @Override
    public void initViews(View view) {
        login = view.findViewById(R.id.google);
        login.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void successResult() {
        DialogUtils.showToast(activity, "Login Successful");
        mListener.onFragmentUpdate(AppConstant.GOTO_HOME, null);
    }

    @Override
    public void failureResult() {
        DialogUtils.showToast(activity, "Login failed");
    }

    @Override
    public void errorMessage(String data_fetching_error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google:
                signIn();
                break;
        }
    }

    private GoogleSignInClient mGoogleSignInClient;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.validateAccount(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
//                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
}
