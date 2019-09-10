package com.buyou.voicetools;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import java.io.File;

public class MyApplication extends Application {
    private static MyApplication INSTANCE = null;
    private static Context context;
    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        context = getApplicationContext();
        initAppDir();
    }
    public static Context getContext() {
        return context;
    }
    /* 文件缓存的目录 */
    public String mAppDir;
    public String mPicturesDir;
    public String mVoicesDir;
    public String mVideosDir;
    public String mFilesDir;

    private void initAppDir() {
        File file = getExternalFilesDir(null);
        if (!file.exists()) {
            file.mkdirs();
        }
        mAppDir = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!file.exists()) {
            file.mkdirs();
        }
        mPicturesDir = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (!file.exists()) {
            file.mkdirs();
        }
        mVoicesDir = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (!file.exists()) {
            file.mkdirs();
        }
        mVideosDir = file.getAbsolutePath();

        file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!file.exists()) {
            file.mkdirs();
        }
        mFilesDir = file.getAbsolutePath();
    }
}
