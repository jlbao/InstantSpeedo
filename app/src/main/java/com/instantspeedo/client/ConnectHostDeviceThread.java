package com.instantspeedo.client;

import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.instantspeedo.connection.WiFiDirectBroadcastReceiver;
import com.instantspeedo.helper.HostDevice;

/**
 * Created by Jialiang on 5/3/15.
 */
public class ConnectHostDeviceThread implements Runnable {

    FindNearbyDevicesActivity activity;

    HostDevice device;

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    WiFiDirectBroadcastReceiver receiver;

    public ConnectHostDeviceThread(FindNearbyDevicesActivity activity, WifiP2pManager manager, WifiP2pManager.Channel channel, WiFiDirectBroadcastReceiver receiver, HostDevice device) {
        this.activity = activity;
        this.manager = manager;
        this.channel = channel;
        this.receiver = receiver;
        this.device = device;
    }

    @Override
    public void run() {
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.VISIBLE);
            Toast.makeText(activity.getApplicationContext(), "Connecting", Toast.LENGTH_LONG).show();
        });
        // device connection logic

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.getWifiP2pDevice().deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        long start = System.currentTimeMillis();
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });

        //----------------end------------
        while (true) {
            if (receiver.getWiFiP2pInfo() != null) {
                if (receiver.getWiFiP2pInfo().groupOwnerAddress != null) {
                    long end = System.currentTimeMillis();
                    Log.d("ConnectHostDeviceThread", Long.toString(end - start) + " ms");
                    activity.runOnUiThread(() -> {
                        activity.loadNearbyDeviceProgressBar.setVisibility(View.GONE);
                        Toast.makeText(activity.getApplicationContext(), device.getDeviceID(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity.getApplicationContext(), SendImageActivity.class);
                        intent.putExtra("HOST_ADDRESS", receiver.getWiFiP2pInfo().groupOwnerAddress.getHostAddress());
                        activity.startActivity(intent);
                    });
                    break;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
