package com.feicuiedu.treasure_20170327.treasure.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feicuiedu.treasure_20170327.R;
import com.feicuiedu.treasure_20170327.custom.TreasureView;
import com.feicuiedu.treasure_20170327.treasure.Treasure;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 宝藏的详情页
public class TreasureDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.detail_treasure)
    TreasureView mTreasureView;
    @BindView(R.id.tv_detail_description)
    TextView mTvDetail;

    private static final String KEY_TREASURE = "key_treasure";
    private Treasure mTreasure;

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

        ButterKnife.bind(this);

        // 拿到传递过来的数据
        mTreasure = (Treasure) getIntent().getSerializableExtra(KEY_TREASURE);

        // toolbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            // 设置标题和返回箭头
            getSupportActionBar().setTitle(mTreasure.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // 处理返回箭头的事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // toolbar的图标的点击事件
    @OnClick(R.id.iv_navigation)
    public void showPopupMenu(View view){
        // 展示出来一个PopupMenu
        /**
         * 1. 创建一个弹出式菜单
         * 2. 菜单项的填充(布局)
         * 3. 设置菜单项的点击监听
         * 4. 显示
         */
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.inflate(R.menu.menu_navigation);
        popupMenu.setOnMenuItemClickListener(mMenuItemClickListener);
        popupMenu.show();
    }

    // 菜单项的点击监听
    private PopupMenu.OnMenuItemClickListener mMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {

        // 点击菜单项会触发：具体根据item的id来判断
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            // TODO: 2017/4/6 步行和骑行的点击事件

            return false;
        }
    };
}
