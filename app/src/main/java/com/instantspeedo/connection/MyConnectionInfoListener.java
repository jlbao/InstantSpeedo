package com.instantspeedo.connection;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

import com.instantspeedo.client.FindNearbyDevicesActivity;

/**
 * Created by Dongyun on 5/1/2015.
 */
public class MyConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener {
    WiFiDirectBroadcastReceiver broadcastReceiver;
    FindNearbyDevicesActivity activity;

    public MyConnectionInfoListener(FindNearbyDevicesActivity activity, WiFiDirectBroadcastReceiver receiver) {
        this.broadcastReceiver = receiver;
        this.activity = activity;
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        broadcastReceiver.wifiP2pInfo = info;
//        activity.runOnUiThread(() -> {
//            activity.jumpToSendImage(info.groupOwnerAddress.getHostName());
//        });
    }

}
