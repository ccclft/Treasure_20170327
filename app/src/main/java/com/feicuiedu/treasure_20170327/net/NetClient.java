package com.feicuiedu.treasure_20170327.net;

import com.feicuiedu.treasure_20170327.user.User;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by gqq on 2017/3/29.
 */

// 网络的客户端类
public class NetClient {

    private static final String BASE_URL = "http://admin.syfeicuiedu.com";
    private static NetClient mNetClient;
    private final OkHttpClient mOkHttpClient;
    private final Gson mGson;

    private NetClient() {

        mGson = new Gson();

        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // 需要设置打印级别
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttpClient的单例化
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    public static synchronized NetClient getInstance(){
        if (mNetClient==null){
            mNetClient = new NetClient();
        }
        return mNetClient;
    }

    // 将每一个请求都单独的放置到一个方法里面
    public Call getData(){
        // 构建请求
        final Request request = new Request.Builder()
                .get()// 请求的方式
                .url("http://www.baidu.com")// 请求的地址
                .addHeader("content-type","html")// 添加请求头信息
                .addHeader("context-length","1024")
                // Get请求不需要添加请求体
                .build();

        // 根据请求进行建模Call
        return mOkHttpClient.newCall(request);
    }

    // 构建POST请求
    public Call login(User user){

        /**
         * 1. 当需要上传的数据是键值对的形式的时候
         *  username = “”；
         *  password = “”；
         *  json = “{username=“”，password = “”}”
         *  一般以表单的形式进行提交
         *
         * 2. 当上传的数据是多个部分的时候
         *  多部分提交
         */
        // 表单形式请求体的构建
//        RequestBody formBody = new FormBody.Builder()
//                .add("username","123456")
//                .add("password","123456")
//                .build();
//
//        // 多部分请求体的构建
//        RequestBody multBody = new MultipartBody.Builder()
//                .addFormDataPart("photo","abc.png",RequestBody.create(null,"abc.png"))
//                .addFormDataPart("name","123456")
//                .build();


        // 需要上传的请求体:字符串、文件、数组等
        RequestBody requestBody = RequestBody.create(null,mGson.toJson(user));

        // 构建的请求
        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL+"/Handler/UserHandler.ashx?action=login")
                .build();

        return mOkHttpClient.newCall(request);
    }
}
