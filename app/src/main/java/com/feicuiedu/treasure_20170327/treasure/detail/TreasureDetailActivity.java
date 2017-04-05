package com.feicuiedu.treasure_20170327.treasure.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicuiedu.treasure_20170327.R;
import com.feicuiedu.treasure_20170327.treasure.Treasure;

// 宝藏的详情页
public class TreasureDetailActivity extends AppCompatActivity {

    private static final String KEY_TREASURE = "key_treasure";

    /**
     * 对外提供一个方法，跳转到本页面
     * 规范一下传递的数据：需要什么参数就必须要传入
     */
    public static void open(Context context, Treasure treasure){
        Intent intent = new Intent(context,TreasureDetailActivity.class);
        intent.putExtra(KEY_TREASURE,treasure);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_detail);
    }
}
