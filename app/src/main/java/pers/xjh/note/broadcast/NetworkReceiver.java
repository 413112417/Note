package pers.xjh.note.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.utils.NetworkUtil;

/**
 * Created by XJH on 2017/5/2.
 */

public class NetworkReceiver extends BroadcastReceiver {

    private static List<OnNetworkChangeListener> listeners;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int networkState = NetworkUtil.getNetworkState(context);
            // 接口回调传过去状态的类型
            if(listeners != null) {
                for(OnNetworkChangeListener listener : listeners) {
                    listener.onNetworkChange(networkState);
                }
            }
        }
    }

    public static void addOnNetworkChangeListener(OnNetworkChangeListener listener) {
        if(listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    // 自定义接口
    public interface OnNetworkChangeListener {
        void onNetworkChange(int networkState);
    }
}
