package com.vikramjha.videoplayer.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.vikramjha.videoplayer.listener.OnFragmentListItemSelectListener;
import com.vikramjha.videoplayer.pojo.VideoList;
import com.vikramjha.videoplayer.utils.utility;
import com.vikramjha.videoplayer.R;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.viewHolder> {

    Context context;
    OnFragmentListItemSelectListener listener;
    ArrayList<VideoList> videoLists;

    public VideoListAdapter(Context context, ArrayList<VideoList> videoLists) {
        this.context = context;
        this.videoLists = videoLists;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false);
        VideoListAdapter.viewHolder viewHolder = new VideoListAdapter.viewHolder(view);
        return viewHolder;
    }

    public void setListner(OnFragmentListItemSelectListener listner) {
        this.listener = listner;
    }

    public void updateVideoList(ArrayList<VideoList> videoLists) {
        this.videoLists = videoLists;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        final VideoList video = videoLists.get(position);
        utility.displayImage(context, video.getThumb(), holder.image_view);
        holder.title.setText(video.getTitle());
        holder.description.setText(video.getDescription());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemSelected(R.id.video_row, video);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView image_view;
        TextView title, description;

        public viewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            image_view = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }
}
