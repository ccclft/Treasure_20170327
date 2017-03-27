package com.feicuiedu.treasure_20170327.user.login;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicuiedu.treasure_20170327.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 登录页面
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_Username)
    EditText mEtUsername;
    @BindView(R.id.et_Password)
    EditText mEtPassword;
    @BindView(R.id.btn_Login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ButterKnife.bind(this);

        // toolbar
        // Toolbar作为ActionBar展示
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!= null){

            // 设置返回的箭头,内部是选项菜单来处理的，而且Android内部已经给他设置好了id
            // android.R.id.home
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // 设置标题
            getSupportActionBar().setTitle(R.string.login);
        }
    }

    // 重写选项菜单的选中监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            // 处理ActionBar上面的返回箭头的事件
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Login)
    public void onClick() {
    }
}
