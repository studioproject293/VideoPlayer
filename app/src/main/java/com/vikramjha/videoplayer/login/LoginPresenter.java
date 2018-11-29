package com.vikramjha.videoplayer.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


import com.vikramjha.videoplayer.utils.DialogUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vikramjha.videoplayer.R;

import static android.support.constraint.Constraints.TAG;


public class LoginPresenter implements ILoginInterface.Presenter {
    ILoginInterface.View view;
    View rootView;
    Activity activity;
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    public LoginPresenter(ILoginInterface.View view) {
        this.view = view;
    }

    @Override
    public void getData(Activity context) {

    }

    @Override
    public void validateAccount(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        DialogUtils.showProgress(activity);
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                            view.successResult();
                        } else {
                            // If sign in fails, display a message to the user.
                            view.failureResult();
                        }

                        // [START_EXCLUDE]
                        DialogUtils.hideProgress();
                        // [END_EXCLUDE]
                    }
                });
    }

    private static final int RC_SIGN_IN = 9001;

    @Override
    public void start() {

    }

    GoogleSignInOptions gso;

    @Override
    public void setView(View view) {
        rootView = view;
        this.view.initViews(rootView);

    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
    }
}
