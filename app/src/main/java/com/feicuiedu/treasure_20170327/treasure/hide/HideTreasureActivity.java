package com.feicuiedu.treasure_20170327.treasure.hide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.mapapi.model.LatLng;
import com.feicuiedu.treasure_20170327.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// 埋藏宝藏的详细页面
public class HideTreasureActivity extends AppCompatActivity {


    private static final String KEY_TITLE = "key_title";
    private static final String KEY_ADDRESS = "key_address";
    private static final String KEY_LATLNG = "key_latlng";
    private static final String KEY_ALTITUDE = "key_altitude";
    @BindView(R.id.hide_send)
    ImageView mHideSend;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_description)
    EditText mEtDescription;

    // 对外提供一个跳转的方法
    public static void open(Context context, String title, String address, LatLng latLng, double altitude) {
        Intent intent = new Intent(context, HideTreasureActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_ADDRESS, address);
        intent.putExtra(KEY_LATLNG, latLng);
        intent.putExtra(KEY_ALTITUDE, altitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_treasure);
        ButterKnife.bind(this);

        // toolbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_TITLE));
        }
    }
}
