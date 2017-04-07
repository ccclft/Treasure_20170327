package com.feicuiedu.treasure_20170327.net;

import com.feicuiedu.treasure_20170327.treasure.Area;
import com.feicuiedu.treasure_20170327.treasure.Treasure;
import com.feicuiedu.treasure_20170327.treasure.detail.TreasureDetail;
import com.feicuiedu.treasure_20170327.treasure.detail.TreasureDetailResult;
import com.feicuiedu.treasure_20170327.treasure.hide.HideTreasure;
import com.feicuiedu.treasure_20170327.treasure.hide.HideTreasureResult;
import com.feicuiedu.treasure_20170327.user.User;
import com.feicuiedu.treasure_20170327.user.login.LoginResult;
import com.feicuiedu.treasure_20170327.user.register.RegisterResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gqq on 2017/3/30.
 */
// 对应服务器的接口
public interface TreasureApi {

    // 登录的请求
    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);

    // 注册的请求
    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

    // 获取区域内的宝藏数据
    @POST("/Handler/TreasureHandler.ashx?action=show")
    Call<List<Treasure>> getTreasureInArea(@Body Area area);

    // 宝藏详情的数据获取
    @POST("/Handler/TreasureHandler.ashx?action=tdetails")
    Call<List<TreasureDetailResult>> getTreasureDetail(@Body TreasureDetail treasureDetail);

    // 埋藏宝藏的请求
    @POST("/Handler/TreasureHandler.ashx?action=hide")
    Call<HideTreasureResult> hideTreasure(@Body HideTreasure hideTreasure);

}
