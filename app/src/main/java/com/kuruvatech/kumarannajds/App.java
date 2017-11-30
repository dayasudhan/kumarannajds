package com.kuruvatech.kumarannajds;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.kuruvatech.kumarannajds.language.LocaleManager;

import java.util.Locale;

/**
 * Created by Mithun-Desk on 11/27/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setLocale(Locale newLocale) {
        Locale.setDefault(newLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = newLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }


}
