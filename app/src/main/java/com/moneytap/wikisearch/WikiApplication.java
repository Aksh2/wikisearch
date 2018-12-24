package com.moneytap.wikisearch;

import android.app.Application;
import android.content.Context;

public class WikiApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

    }

    public static Context getAppContext(){
        return context;
    }
}
