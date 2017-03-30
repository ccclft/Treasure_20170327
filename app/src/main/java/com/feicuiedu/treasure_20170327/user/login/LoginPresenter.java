package com.feicuiedu.treasure_20170327.user.login;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.feicuiedu.treasure_20170327.net.NetClient;
import com.feicuiedu.treasure_20170327.user.User;
import com.feicuiedu.treasure_20170327.user.UserResult;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by gqq on 2017/3/28.
 */

// 登录的业务类：帮View去做业务请求
public class LoginPresenter {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 业务类中间涉及到的视图怎么处理？
     * 1. 创建LoginActivity，不能采用这种方式
     * 2. 接口回调的方式
     * A 接口  里面有一个a()
     * AImpl是A接口的实现类  实现a()
     * 使用：A a = new Aimpl();
     * this.a = a;
     * a.a();
     * <p>
     * 接口创建好了，怎么初始化？
     * Activity实现视图接口
     */

    private LoginView mLoginView;
    private Call mCall;

    public LoginPresenter(LoginView loginView) {
        mLoginView = loginView;
    }

    // 登录的业务
    public void login(User user) {

        NetClient.getInstance().login(user).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.showMessage("请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (response.isSuccessful()){
                            ResponseBody responseBody = response.body();
                            try {
                                String json = responseBody.string();

                                UserResult userResult = new Gson().fromJson(json, UserResult.class);

                                if (userResult==null){
                                    mLoginView.showMessage("未知的错误");
                                    return;
                                }

                                if (userResult.getCode()==1){
                                    mLoginView.navigateToHome();
                                }
                                mLoginView.showMessage(userResult.getMsg());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}
