package com.vikramjha.videoplayer.video;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.vikramjha.videoplayer.VideoApplication;
import com.vikramjha.videoplayer.base.BaseFragment;
import com.vikramjha.videoplayer.listener.OnFragmentListItemSelectListener;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.vikramjha.videoplayer.utils.DialogUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.video.VideoListener;
import com.vikramjha.videoplayer.R;

import java.util.ArrayList;

public class VideoFragment extends BaseFragment implements IVideoInterface.View, OnFragmentListItemSelectListener, PlayerControlView.VisibilityListener, PlaybackPreparer, VideoListener {
    static VideoList video;
    RecyclerView related;
    TextView title, description;
    RelatedVideoAdapter adapter;
    PlayerView video_view;

    public static VideoFragment getInstance(VideoList videos) {
        video = videos;
        return new VideoFragment();
    }

    Activity activity;
    View rootView;
    IVideoInterface.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.video_player, container, false);
        activity = getActivity();
        presenter = new VideoPresenter(this);
        presenter.setView(rootView);
        return rootView;
    }

    @Override
    public void setAdapterData(ArrayList<VideoList> videoLists) {
        if (videoLists != null && videoLists.size() > 0) {
            if (adapter == null)
                adapter = new RelatedVideoAdapter(activity, videoLists);
            else
                adapter.updateVideoList(videoLists);
            adapter.setListner(this);
            related.setAdapter(adapter);
        }
    }

    @Override
    public void initViews(View view) {
        related = view.findViewById(R.id.related);
        related.setLayoutManager(new LinearLayoutManager(activity));
        title = view.findViewById(R.id.title);
        title.setText(video.getTitle());
        description = view.findViewById(R.id.description);
        description.setText(video.getDescription());
        video_view = view.findViewById(R.id.video_view);

        initializePlayer();
    }

    SimpleExoPlayer player;

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(activity),
                new DefaultTrackSelector(), new DefaultLoadControl());

        video_view.setPlayer(player);
        video_view.setControllerVisibilityListener(this);
        video_view.requestFocus();
        video_view.setPlaybackPreparer(this);

        Uri uri = Uri.parse(video.getUrl());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        Log.d("position", VideoApplication.getInstance().initDB().videoListDao().getPosition(video.getUrl()) + "");
        player.seekTo(VideoApplication.getInstance().initDB().videoListDao().getPosition(video.getUrl()));
        player.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void successResult() {

    }

    long playbackPosition;
    int currentWindow;
    boolean playWhenReady;

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            video.setPlaybackPosition(playbackPosition);
            presenter.updateVideoPosition(video);
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
//        DialogUtils.showToast(activity, "Pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getRelatedVideos(this,video);
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    public void failureResult() {

    }

    @Override
    public void errorMessage(String data_fetching_error) {

    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        switch (itemId) {
            case R.id.video_row:
                releasePlayer();
                video = (VideoList) data;
                initializePlayer();
                presenter.getRelatedVideos(this, video);
                break;
        }
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    @Override
    public void preparePlayback() {
        DialogUtils.showToast(activity, "Player Ready");
    }


    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

    }

    @Override
    public void onRenderedFirstFrame() {

    }
}
