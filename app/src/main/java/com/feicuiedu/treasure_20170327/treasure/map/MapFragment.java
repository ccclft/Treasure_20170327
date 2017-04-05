package com.feicuiedu.treasure_20170327.treasure.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.feicuiedu.treasure_20170327.R;
import com.feicuiedu.treasure_20170327.treasure.Area;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gqq on 2017/3/31.
 */
// 地图和宝藏的展示
public class MapFragment extends Fragment {

    private static final int LOCATION_REQUEST_CODE = 100;
    @BindView(R.id.iv_located)
    ImageView mIvLocated;
    @BindView(R.id.btn_HideHere)
    Button mBtnHideHere;
    @BindView(R.id.centerLayout)
    RelativeLayout mCenterLayout;
    @BindView(R.id.iv_scaleUp)
    ImageView mIvScaleUp;
    @BindView(R.id.iv_scaleDown)
    ImageView mIvScaleDown;
    @BindView(R.id.tv_located)
    TextView mTvLocated;
    @BindView(R.id.tv_satellite)
    TextView mTvSatellite;
    @BindView(R.id.tv_compass)
    TextView mTvCompass;
    @BindView(R.id.ll_locationBar)
    LinearLayout mLlLocationBar;
    @BindView(R.id.tv_currentLocation)
    TextView mTvCurrentLocation;
    @BindView(R.id.iv_toTreasureInfo)
    ImageView mIvToTreasureInfo;
    @BindView(R.id.et_treasureTitle)
    EditText mEtTreasureTitle;
    @BindView(R.id.cardView)
    CardView mCardView;
    @BindView(R.id.layout_bottom)
    FrameLayout mLayoutBottom;
    @BindView(R.id.map_frame)
    FrameLayout mMapFrame;
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private LocationClient mLocationClient;
    private boolean isFirst = true;
    private LatLng mCurrentLocation;
    private String mCurrentAddr;
    private LatLng mCurrentStatus;
    private MapPresenter mMapPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container);

//        // 1. 检测权限有没有授权成功
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // 没有成功，需要向用户申请
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    LOCATION_REQUEST_CODE);
//        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mMapPresenter = new MapPresenter();
        // 视图的处理工作

        // 初始化百度地图
        initMapView();

        // 初始化定位相关
        initLocation();

    }

    // 初始化定位相关
    private void initLocation() {

        // 前置：激活定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 1. 第一步，初始化LocationClient类
        mLocationClient = new LocationClient(getContext().getApplicationContext());

        // 2. 第二步，配置定位SDK参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开GPS
        option.setCoorType("bd09ll");// 设置坐标类型，默认gcj02，会有偏差，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);// 需要地址信息
        // 设置参数给LocationClient
        mLocationClient.setLocOption(option);

        // 3. 第三步，实现BDLocationListener接口
        mLocationClient.registerLocationListener(mBDLocationListener);

        // 4. 第四步，开始定位
        mLocationClient.start();

    }

    // 定位监听
    private BDLocationListener mBDLocationListener = new BDLocationListener() {

        // 当获取到定位数据的时候会触发
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation==null){
                // 没有拿到数据，可以重新进行请求
                mLocationClient.requestLocation();
                return;
            }

            // 拿到定位的经纬度
            double latitude = bdLocation.getLatitude();
            double longitude = bdLocation.getLongitude();

            // 定位的位置和地址
            mCurrentLocation = new LatLng(latitude,longitude);
            mCurrentAddr = bdLocation.getAddrStr();

            Log.i("TAG","定位的位置："+ mCurrentAddr +"经纬度："+latitude+","+longitude);

            // 地图上设置定位数据
            MyLocationData locationData = new MyLocationData.Builder()
                    // 设置定位的经纬度
                    .latitude(latitude)
                    .longitude(longitude)
                    .accuracy(100f)// 定位精度的大小
                    .build();

            mBaiduMap.setMyLocationData(locationData);

            // 第一次进入将地图自动移动到定位的位置
            if (isFirst){
                // 自动移动到定位处
                moveToLocation();
                isFirst = false;
            }
        }
    };

    // 初始化百度地图的操作
    private void initMapView() {

        // 地图的状态
        MapStatus mapstatus = new MapStatus.Builder()
                .rotate(0)// 旋转的角度
                .zoom(15)// 默认是12，范围3-21
                .overlook(0)// 俯仰的角度
                .build();

        // 设置地图的信息
        BaiduMapOptions options = new BaiduMapOptions()
                .mapStatus(mapstatus)
                .compassEnabled(true)// 是否显示指南针，默认显示
                .zoomGesturesEnabled(true)// 是否允许缩放手势
                .scaleControlEnabled(false)// 不显示比例尺
                .zoomControlsEnabled(false)// 不显示缩放的控件
                ;

        // 创建地图控件
        mMapView = new MapView(getContext(), options);

        // 在布局中添加地图的控件：0，放置在第一位
        mMapFrame.addView(mMapView,0);

        // 拿到地图的操作类(设置地图的视图、地图状态变化、添加覆盖物等)
        mBaiduMap = mMapView.getMap();

        // 设置地图状态的监听
        mBaiduMap.setOnMapStatusChangeListener(mStatusChangeListener);
    }

    // 地图状态的监听
    private BaiduMap.OnMapStatusChangeListener mStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {

        // 变化开始的时候
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        // 变化中
        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        // 变化结束之后
        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {

            // 拿到当前移动后的地图状态所在的位置
            LatLng target = mapStatus.target;

            // 地图状态确实发生变化了
            if(target != MapFragment.this.mCurrentStatus){

                // 根据当前的地图的状态来获取当前的区域内的宝藏数据
                updateMapArea();

                // 当前地图的位置
                MapFragment.this.mCurrentStatus = target;
            }
        }
    };

    // 区域的确定和宝藏数据的获取
    private void updateMapArea() {

        // 拿到当前的地图状态
        MapStatus mapStatus = mBaiduMap.getMapStatus();

        // 从中拿到当前地图的经纬度
        double longitude = mapStatus.target.longitude;
        double latitude = mapStatus.target.latitude;

        // 根据当前的经纬度来确定区域
        Area area = new Area();

        // 根据当前经纬度向上和向下取整得到的区域
        area.setMaxLat(Math.ceil(latitude));
        area.setMaxLng(Math.ceil(longitude));
        area.setMinLng(Math.floor(longitude));
        area.setMinLat(Math.floor(latitude));

        // 要根据当前的区域来获取了：进行网络请求
        mMapPresenter.getTreasure(area);
    }

    // 卫星视图和普通视图的切换
    @OnClick(R.id.tv_satellite)
    public void switchMapType(){

        // 先拿到当前的地图的类型
        int mapType = mBaiduMap.getMapType();
        // 切换类型
        mapType = (mapType==BaiduMap.MAP_TYPE_NORMAL)?BaiduMap.MAP_TYPE_SATELLITE:BaiduMap.MAP_TYPE_NORMAL;
        // 文字的变化
        String msg = (mapType==BaiduMap.MAP_TYPE_NORMAL)?"卫星":"普通";
        mBaiduMap.setMapType(mapType);
        mTvSatellite.setText(msg);
    }

    @OnClick(R.id.tv_compass)
    public void switchCompass(){
        // 当前地图指南针有没有在显示
        boolean enabled = mBaiduMap.getUiSettings().isCompassEnabled();
        mBaiduMap.getUiSettings().setCompassEnabled(!enabled);
    }

    // 地图的缩放
    @OnClick({R.id.iv_scaleDown,R.id.iv_scaleUp})
    public void scaleMap(View view){
        switch (view.getId()){
            case R.id.iv_scaleUp:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.iv_scaleDown:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
        }
    }

    // 定位按钮：移动到定位的位置
    @OnClick(R.id.tv_located)
    public void moveToLocation(){
        // 更新的是地图的状态
        MapStatus mapStatus = new MapStatus.Builder()
                .target(mCurrentLocation)// 定位的位置
                .rotate(0)
                .overlook(0)
                .zoom(19)
                .build();
        // 更新的状态
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
        // 利用地图操作类更新地图的状态
        mBaiduMap.animateMapStatus(update);
    }

    // 处理权限的回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch(requestCode){
//            case LOCATION_REQUEST_CODE:
//
//                // 用户授权成功了
//                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    // 定位了
//                    mLocationClient.requestLocation();
//                }else {
//                    // 显示个吐司、提示框
//                }
//                break;
//        }
//    }
}
