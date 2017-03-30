package com.feicuiedu.treasure_20170327.net;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by gqq on 2017/3/30.
 */
// 对应服务器的接口
public interface TreasureApi {

    // 构建请求
    @GET("http://www.baidu.com")
    @Headers({"content-type:html","context-length:1024"})
    Call<ResponseBody> getData();
}
