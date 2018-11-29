package com.vikramjha.videoplayer.home;

import android.app.Activity;
import android.view.View;

import com.vikramjha.videoplayer.VideoListApi;
import com.vikramjha.videoplayer.cache.AppCache;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.vikramjha.videoplayer.utils.utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements IHomeInterface.Presenter {
    IHomeInterface.View view;
    View rootView;
    VideoListApi videoListApi;

    public HomePresenter(IHomeInterface.View view) {
        this.view = view;

    }

    private void initPresenter(View rootView) {
        view.initViews(rootView);
    }


    @Override
    public void getData(Activity context) {
        final ArrayList<VideoList> videoLists = AppCache.getInstance().getVideoLists();
        if (videoLists != null && videoLists.size() > 0) {
            view.setAdapterData(videoLists);
        } else {
            videoListApi = utility.getRetrofit(context).create(VideoListApi.class);
            Call<ArrayList<VideoList>> response = videoListApi.videoListResponse("pretty");
            response.enqueue(new Callback<ArrayList<VideoList>>() {

                @Override
                public void onResponse(Call<ArrayList<VideoList>> call, Response<ArrayList<VideoList>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<VideoList> videos = response.body();
                        AppCache.getInstance().setVideoLists(videos);
                        view.setAdapterData(videos);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ArrayList<VideoList>> call, Throwable t) {
                    view.errorMessage("Data Fetching Error");
                }

            });
        }
            /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("VideoList");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    GenericTypeIndicator<ArrayList<VideoList>> typeIndicator = new GenericTypeIndicator<ArrayList<VideoList>>() {
                    };
                    ArrayList<VideoList> videos = dataSnapshot.getValue(typeIndicator);
                    AppCache.getInstance().setVideoLists(videos);
                    view.setAdapterData(videos);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                    view.errorMessage("Data Fetching Error");
                }
            });*/

    }



    @Override
    public void getLoginData(String userName, String Password) {

    }

    @Override
    public void start() {

    }

    @Override
    public void setView(View view) {
        rootView = view;
        initPresenter(rootView);
    }

    @Override
    public void setActivity(Activity activity) {

    }
}
