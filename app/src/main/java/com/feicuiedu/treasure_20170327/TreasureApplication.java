package com.feicuiedu.treasure_20170327;

import android.app.Application;

import com.feicuiedu.treasure_20170327.user.UserPrefs;

/**
 * Created by gqq on 2017/3/31.
 */

public class TreasureApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        // 用户仓库的初始化
        UserPrefs.init(getApplicationContext());

    }
}
