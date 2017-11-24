package com.kuruvatech.kumarannajds.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.model.FeedItem;

import java.util.ArrayList;

/**
 * Created by ofaroque on 8/13/15.
 */
public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<YoutubeRecyclerAdapter.VideoInfoHolder> {

    //these ids are the unique id for each video
   // String[] VideoID = {"P3mAtvs5Elc", "nCgQDjiotG0", "P3mAtvs5Elc"};
//    String[] VideoID = {"jRc391TzTQU", "Wq11cKL5qnM","LQ7KKmc7Wcw", "KWLd6eYnSKI" ,
//            "WffZCNAM5tE","hJM4Fh1D6LE","JWn-44z4ayw","KpGyPb2nqh0",
//    "OYUtK9FGNpw","CNO19isWnxg","6JkY58joCGo","GlOPwJhAulc"};
    ArrayList<FeedItem> feedList;
    Context ctx;
    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
    public YoutubeRecyclerAdapter(Context context , ArrayList<FeedItem> afeedList) {
        this.ctx = context;
        feedList = afeedList;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };
        holder.heading.setText(feedList.get(position).getHeading());
        holder.youTubeThumbnailView.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(feedList.get(position).getVideoid());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        protected ImageView shareButton;
        TextView heading;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            heading=(TextView)itemView.findViewById(R.id.video_heading);
            shareButton=(ImageView)itemView.findViewById(R.id.imagesharebutton);
            shareButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent shareIntent = new Intent();
                     shareIntent.setType("text/html");
                    String youtube_link = "https://www.youtube.com/watch?v=";
                    youtube_link = youtube_link + feedList.get(getLayoutPosition()).getVideoid();
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,  "Subject");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, youtube_link);
                    shareIntent.setAction(Intent.ACTION_SEND);
                    ctx.startActivity(Intent.createChooser(shareIntent, "Share it ...."));
                    }



            });
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {

            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, API_KEY, feedList.get(getLayoutPosition()).getVideoid(),0,true,false);
            ctx.startActivity(intent);
        }
    }
}