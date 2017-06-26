package pers.xjh.note.ui.detail.android;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.PermissionUtil;

/**
 * Created by XJH on 2017/5/25.
 */
public class GPSActivity extends BaseActivity {

    private LocationManager mLocationManager;

    private TextView mTvGPSStatus, mTvLongitude, mTvLatitude, mTvAltitude;
    //最佳查询条件
    private String mBestProvider;

    @Override
    protected int initContentView() {
        return R.layout.activity_gps;
    }

    @Override
    protected void initView() {
        mTvGPSStatus = (TextView) findViewById(R.id.tv_gps_status);
        mTvLongitude = (TextView) findViewById(R.id.tv_longitude);
        mTvLatitude = (TextView) findViewById(R.id.tv_latitude);
        mTvAltitude = (TextView) findViewById(R.id.tv_altitude);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (PermissionUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // 为获取地理位置信息时设置查询条件
            mBestProvider = mLocationManager.getBestProvider(getCriteria(), true);
            // 获取位置信息
            // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
            Location location = mLocationManager.getLastKnownLocation(mBestProvider);
            updateView(location);

            // 监听状态
//            mLocationManager.registerGnssStatusCallback(listener);
            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

            // 1秒更新一次，或最小位移变化超过1米更新一次；
            // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
        } else {
            PermissionUtil.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PermissionUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    /**
     * 返回查询条件
     *
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(true);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(true);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //检查权限是否授予
        for(int result : grantResults) {
            if(result != PackageManager.PERMISSION_GRANTED) {
                finish();
                return;
            }
        }

        if (PermissionUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
        }
    }

    private LocationListener mLocationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        @Override
        public void onLocationChanged(Location location) {
            mTvGPSStatus.setText("当前位置发生变化");
            updateView(location);
        }

        /**
         * GPS状态变化时触发
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    mTvGPSStatus.setText("当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    mTvGPSStatus.setText("当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    mTvGPSStatus.setText("当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        @Override
        public void onProviderEnabled(String provider) {
            mTvGPSStatus.setText("当前GPS状态为开启状态");
            if (PermissionUtil.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Location location = mLocationManager.getLastKnownLocation(provider);
                updateView(location);
            }
        }

        /**
         * GPS禁用时触发
         */
        @Override
        public void onProviderDisabled(String provider) {
            mTvGPSStatus.setText("当前GPS状态为禁用状态");
            updateView(null);
        }
    };

    /**
     * 更新界面
     * @param location
     */
    private void updateView(Location location) {
        if(location != null) {
            mTvLongitude.setText("经度:" + location.getLongitude());
            mTvLatitude.setText("纬度:" + location.getLatitude());
            mTvAltitude.setText("海拔:" + location.getAltitude());
        }
    }
}
