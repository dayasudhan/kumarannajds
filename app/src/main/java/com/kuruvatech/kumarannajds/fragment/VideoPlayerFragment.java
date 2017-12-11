package com.kuruvatech.kumarannajds.fragment;
import com.kuruvatech.kumarannajds.CustomMediaPlayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.utils.Constants;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by dayas on 09-12-2017.
 */

public class VideoPlayerFragment extends Fragment{

    Button btnshareApp;
    //ProgressDialog pDialog;
    ProgressBar progressBar ;
    VideoView videoview;
    ImageView imageView;
    MediaController mediacontroller;
    private boolean isViewShown = false;
    private boolean isViewShown2 = false;
    ImageView playButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(isViewShown) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.videoview_main, container, false);
            videoview = (VideoView) view.findViewById(R.id.VideoView);
            playButton = (ImageView) view.findViewById(R.id.btnvideo_player);

            progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);

            String VideoURL = "https://chunavane.s3.ap-south-1.amazonaws.com/apptest/video/main1512913581803.mp4";

            progressBar.setVisibility(View.VISIBLE);

            try {
                // Start the MediaController
                mediacontroller = new MediaController(
                        getActivity());

                Uri video = Uri.parse(VideoURL);

                videoview.setVideoURI(video);

            } catch (Exception e) {

                e.printStackTrace();
            }

           // videoview.requestFocus();
            videoview.setOnPreparedListener(new OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {

                    progressBar.setVisibility(View.GONE);
                    videoview.seekTo(100);


                }
            });

            videoview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CustomMediaPlayer.class);

                    intent.putExtra("url", "https://chunavane.s3.ap-south-1.amazonaws.com/apptest/video/main1512913581803.mp4");
                    startActivity(intent);
                }
            });
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CustomMediaPlayer.class);

                    intent.putExtra("url", "https://chunavane.s3.ap-south-1.amazonaws.com/apptest/video/main1512913581803.mp4");
                    startActivity(intent);
                }
            });
            return view;
        }
        return null;
    }
//    @Override
//    public void onPause() {
//
//        super.onPause();
//        mediacontroller.hide();
//       // pDialog.hide();
//        //; pDialog.setVisibility(View.GONE);
//        mediacontroller.setVisibility(View.GONE);
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//    }

   @Override
    public void onStop() {
        super.onStop();
        mediacontroller.hide();
      //  pDialog.hide();
       videoview.stopPlayback();
        //; pDialog.setVisibility(View.GONE);
        mediacontroller.setVisibility(View.GONE);

    }
    @Override
    public void onPause() {
        super.onPause();
      //  videoview.pause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        videoview.suspend();
        videoview = null;

    }
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible)
        {
            isViewShown = true;
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
//        if (getView() != null) {
//            isViewShown = true;
//        }
//        else {
//            isViewShown = false;
//        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        isViewShown = true;
//        if (!getUserVisibleHint())
//    {
//        return;
//    }

        //INSERT CUSTOM CODE HERE
    }
    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


}

