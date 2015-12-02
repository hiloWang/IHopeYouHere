package com.hilo.others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.hilo.R;
import com.hilo.util.LogUtils;

import java.util.LinkedList;

/**
 * Created by hilo on 15/12/1.
 * <p/>
 * Drscription:
 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Configuration cfg = Configuration.getConfig();
        Configuration.saveConfig();

        cfg.deviceType = 1;
        if (getResources().getBoolean(R.bool.isTablet)) cfg.deviceType = 2;
        cfg.deviceName = android.os.Build.BRAND + "_" + android.os.Build.PRODUCT;
        LogUtils.I("klog", "deviceName: " + cfg.deviceName);
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(this);
    }
}
