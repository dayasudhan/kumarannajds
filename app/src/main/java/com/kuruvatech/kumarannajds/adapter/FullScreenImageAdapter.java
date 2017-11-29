package com.kuruvatech.kumarannajds.adapter;

/**
 * Created by dayas on 29-11-2017.
 */
import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.utils.ImageLoader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;
    public ImageLoader imageLoader;
    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
        imageLoader = new ImageLoader(activity,500,500);
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.activity_single_view, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.SingleView);
     //   btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        imageLoader.DisplayImage(_imagePaths.get(position), imgDisplay);
//        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
//        imgDisplay.setImageBitmap(bitmap);

        // close button click event
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                _activity.finish();
//            }
//        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }
    private void setToolBar(String areaClicked) {

//        tb.setTitleTextColor(Color.rgb(Constants.TITLE_TEXT_COLOR_RED,
//                Constants.TITLE_TEXT_COLOR_GREEN, Constants.TITLE_TEXT_COLOR_BLUE));
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}