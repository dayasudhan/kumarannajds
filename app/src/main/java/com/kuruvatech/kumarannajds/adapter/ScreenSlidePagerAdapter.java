package com.kuruvatech.kumarannajds.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuruvatech.kumarannajds.fragment.ScreenSlidePageFragment;
import com.kuruvatech.kumarannajds.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        private List<String> picList = new ArrayList<>();
    Context mContext;
    public ScreenSlidePagerAdapter(FragmentManager fm , Context context) {

        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        SessionManager session = new SessionManager(mContext);
        return ScreenSlidePageFragment.newInstance(session.getSlider().get(i));
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    public void addAll(List<String> picList) {
        this.picList = picList;
    }
}
