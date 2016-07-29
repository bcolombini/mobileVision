package com.example.bruno.mobilevision;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by bruno on 29/07/16.
 */
public class MobileVisionApplication extends Application{
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
