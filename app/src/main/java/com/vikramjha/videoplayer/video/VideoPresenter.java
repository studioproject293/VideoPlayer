package com.vikramjha.videoplayer.video;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.vikramjha.videoplayer.VideoApplication;
import com.vikramjha.videoplayer.cache.AppCache;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideoPresenter implements IVideoInterface.Presenter {

    IVideoInterface.View view;
    View rootView;


    public VideoPresenter(IVideoInterface.View view) {
        this.view = view;
    }


    @Override
    public void getData(Activity context) {

    }

    @Override
    public void getRelatedVideos(final VideoList video) {
        final ArrayList<VideoList> videoLists = AppCache.getInstance().getVideoLists();
        if (videoLists != null && videoLists.size() > 0) {
            ArrayList<VideoList> relatedList = new ArrayList<>(videoLists);
            relatedList.remove(video);
            view.setAdapterData(relatedList);
        } else {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                    ArrayList<VideoList> relatedList = new ArrayList<>(videoLists);
                    relatedList.remove(video);
                    view.setAdapterData(relatedList);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                    view.errorMessage("Data Fetching Error");
                }
            });
        }
    }

    @Override
    public void updateVideoPosition(VideoList video) {
        int count = VideoApplication.getInstance().initDB().videoListDao().countByUrl(video.getUrl());
        if (count == 1) {
            VideoApplication.getInstance().initDB().videoListDao().update(video);
        } else {
            VideoApplication.getInstance().initDB().videoListDao().insert(video);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void setView(View view1) {
        rootView = view1;
        view.initViews(rootView);
    }

    @Override
    public void setActivity(Activity activity) {

    }
}