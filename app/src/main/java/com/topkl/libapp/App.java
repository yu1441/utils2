package com.topkl.libapp;

import android.app.Application;
import android.util.Log;

import com.yujing.ycrash.YCrash;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YCrash.getInstance().setAppName("libs_App");
        YCrash.getInstance().setCrashInfoListener(new YCrash.CrashInfoListener() {
            @Override
            public void info(YCrash.AppInfo appInfo) {
                Log.e("异常信息", appInfo.崩溃信息);
            }
        });
        YCrash.getInstance().init(getApplicationContext());
    }
}
