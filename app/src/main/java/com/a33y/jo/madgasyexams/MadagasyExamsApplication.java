package com.a33y.jo.madgasyexams;

import android.app.Application;
import android.content.Context;

public class MadagasyExamsApplication extends Application {
    private static MadagasyExamsApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}

