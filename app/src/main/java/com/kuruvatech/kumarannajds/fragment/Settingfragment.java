package com.kuruvatech.kumarannajds.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kuruvatech.kumarannajds.App;
import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.utils.Constants;

import java.util.Locale;

/**
 * Created by dayas on 02-12-2017.
 */

public class Settingfragment extends Fragment {
    Switch switchLanguage;
    private static boolean isEnglish = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.settinglayout, container, false);
//        ((MainActivity) getActivity())
//                .setActionBarTitle("Invite Friends");
        switchLanguage = (Switch) view.findViewById(R.id.language);
        switchLanguage.setChecked(!isEnglish);
        switchLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    ((App) getActivity().getApplication()).setLocale(new Locale("kn"));
                    isEnglish=false;
                } else {
                    isEnglish=true;
                    ((App) getActivity().getApplication()).setLocale(new Locale("en"));

                }
                refreshUI();
            }
        });
        return view;
    }
    private ProgressDialog mProgressDialog;

    public void createProgressBar() {

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Signing........");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
    }

    private void refreshUI() {
       // mProgressDialog.show();
        getActivity().recreate();
       // mProgressDialog.hide();
    }


    //    public void setLocale(String lang) { //call this in onCreate()
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//
//    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
