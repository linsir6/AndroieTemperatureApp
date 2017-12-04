package com.qf58.temperature;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by linSir
 * date at 2017/12/3.
 * describe:
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
