package com.feicuiedu.treasure_20170327.user.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicuiedu.treasure_20170327.MainActivity;
import com.feicuiedu.treasure_20170327.R;
import com.feicuiedu.treasure_20170327.commons.ActivityUtils;
import com.feicuiedu.treasure_20170327.commons.RegexUtils;
import com.feicuiedu.treasure_20170327.custom.AlertDialogFragment;
import com.feicuiedu.treasure_20170327.treasure.HomeActivity;

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
    private String mUserName;
    private String mPassword;
    private ProgressDialog mProgressDialog;
    private ActivityUtils mActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        mActivityUtils = new ActivityUtils(this);

        ButterKnife.bind(this);

        // toolbar
        // Toolbar作为ActionBar展示
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            // 设置返回的箭头,内部是选项菜单来处理的，而且Android内部已经给他设置好了id
            // android.R.id.home
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // 设置标题
            getSupportActionBar().setTitle(R.string.login);
        }

        // 设置文本变化的监听
        mEtUsername.addTextChangedListener(mTextWatcher);
        mEtPassword.addTextChangedListener(mTextWatcher);

    }

    // 文本变化的监听
    private TextWatcher mTextWatcher = new TextWatcher() {

        // 文本变化前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // 文本变化中
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        // 文本变化后
        @Override
        public void afterTextChanged(Editable s) {
            mUserName = mEtUsername.getText().toString();
            mPassword = mEtPassword.getText().toString();

            // 判断用户名和密码都不为空，按钮才可以点击
            boolean canLogin = !(TextUtils.isEmpty(mUserName) || TextUtils.isEmpty(mPassword));
            mBtnLogin.setEnabled(canLogin);
        }
    };

    // 重写选项菜单的选中监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // 处理ActionBar上面的返回箭头的事件
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Login)
    public void onClick() {

        // 账号不符合规则
        if (RegexUtils.verifyUsername(mUserName) != RegexUtils.VERIFY_SUCCESS) {

            // 弹个对话框提示
            AlertDialogFragment.getInstances(getString(R.string.username_error),
                    getString(R.string.username_rules))
                    .show(getSupportFragmentManager(),"usernameError");
            return;

        }

        // 密码不符合规范
        if (RegexUtils.verifyPassword(mPassword) != RegexUtils.VERIFY_SUCCESS) {
            // 弹个对话框

            AlertDialogFragment.getInstances(getString(R.string.password_error),
                    getString(R.string.password_rules))
                    .show(getSupportFragmentManager(),"passwordError");
            return;
        }

        // 要去做登录的业务逻辑,模拟用户登录的场景，异步任务来模拟
        /**
         * 1. 参数：请求的地址、上传的数据等类型，可以为空Void
         * 2. 进度：一般是Integer(int的包装类)，可以为空Void
         * 3. 结果：比如String、可以为空Void
         */
        new AsyncTask<Void,Integer,Void>(){

            // 请求之前的视图处理：比如进度条的显示
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // 显示一个进度条
                showProgress();
            }

            // 后台线程：耗时的操作
            @Override
            protected Void doInBackground(Void... params) {

                // 模拟：休眠3秒钟
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }

            // 拿到请求的数据，处理UI：进度条隐藏、跳转页面等
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                // 隐藏进度条
                hideProgress();
                showMessage("登录成功");
                navigateToHome();

            }
        }.execute();
    }

    //----------------------登录的业务过程中涉及的视图处理------------------------
    // 跳转页面
    private void navigateToHome() {
        mActivityUtils.startActivity(HomeActivity.class);
        finish();

        // MainActivity是不是也需要关闭：发个本地广播的形式关闭
        Intent intent = new Intent(MainActivity.MAIN_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    // 显示信息
    private void showMessage(String message) {
        mActivityUtils.showToast(message);
    }

    // 隐藏进度条
    private void hideProgress() {
        if (mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    // 进度条的显示
    private void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "登录", "正在登录中，请稍后~");
    }
}
