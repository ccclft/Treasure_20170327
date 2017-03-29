package com.feicuiedu.treasure_20170327.net;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by gqq on 2017/3/29.
 */

// 网络的客户端类
public class NetClient {

    private static NetClient mNetClient;
    private final OkHttpClient mOkHttpClient;

    private NetClient() {

        // OkHttpClient的单例化
        mOkHttpClient = new OkHttpClient();
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

}
