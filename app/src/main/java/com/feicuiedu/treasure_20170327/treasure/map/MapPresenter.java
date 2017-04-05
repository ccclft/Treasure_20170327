package com.feicuiedu.treasure_20170327.treasure.map;

import android.util.Log;

import com.feicuiedu.treasure_20170327.commons.LogUtils;
import com.feicuiedu.treasure_20170327.net.NetClient;
import com.feicuiedu.treasure_20170327.treasure.Area;
import com.feicuiedu.treasure_20170327.treasure.Treasure;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/4/5.
 */

// 获取宝藏数据的业务类
public class MapPresenter {

    // 获取宝藏数据
    public void getTreasure(Area area){
        Call<List<Treasure>> listCall = NetClient.getInstance().getTreasureApi().getTreasureInArea(area);
        listCall.enqueue(mListCallback);
    }

    private Callback<List<Treasure>> mListCallback = new Callback<List<Treasure>>() {
        // 请求成功
        @Override
        public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {
            // 成功
            if (response.isSuccessful()){
                // 拿到响应体数据
                List<Treasure> treasureList = response.body();

                if (treasureList==null){
                    // 弹个吐司说明一下
                    return;
                }
                // 拿到数据：给MapFragment设置上，在地图上展示
                LogUtils.i("请求的数据："+treasureList.size());

            }
        }

        // 请求失败
        @Override
        public void onFailure(Call<List<Treasure>> call, Throwable t) {
            // 弹个吐司
            LogUtils.i("请求失败"+t.getMessage());
        }
    };
}
