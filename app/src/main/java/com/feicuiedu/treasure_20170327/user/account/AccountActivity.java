package com.feicuiedu.treasure_20170327.user.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicuiedu.treasure_20170327.R;
import com.feicuiedu.treasure_20170327.commons.ActivityUtils;
import com.feicuiedu.treasure_20170327.user.UserPrefs;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 个人信息页面
public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.account_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_usericon)
    ImageView mIVIcon;

    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        mActivityUtils = new ActivityUtils(this);

        // toolbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.account_msg);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String photo = UserPrefs.getInstance().getPhoto();
        if (photo!=null){
            // 加载头像
            Picasso.with(this)
                    .load(photo)
                    .into(mIVIcon);
        }
    }

    // 点击头像：弹出一个弹窗(自定义一个PopupWindow)
    @OnClick(R.id.iv_usericon)
    public void showPhotoWindow(){
        // 吐司
        mActivityUtils.showToast("点击了头像");
    }

    // 返回箭头的处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
