package com.instantspeedo.client;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.instantspeedo.helper.ClientShared;
import com.instantspeedo.helper.DeviceHelper;

import java.util.TimerTask;

/**
 * Created by Jialiang on 4/14/15.
 * Find device thread
 */
public class FindHostDevicesThread extends TimerTask {

    FindNearbyDevicesActivity activity;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    public FindHostDevicesThread(FindNearbyDevicesActivity activity, WifiP2pManager manager, WifiP2pManager.Channel channel) {
        this.activity = activity;
        this.manager = manager;
        this.channel = channel;
    }

    @Override
    public void run() {
        // show the loading bar
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.VISIBLE);
            Toast.makeText(activity.getApplicationContext(), "Finding hosts", Toast.LENGTH_SHORT).show();
        });
        // this will update the device list in client shared class
        long start = System.currentTimeMillis();
        DeviceHelper.findNearbyHostDevices(manager, channel);
        while (true) {
            if (!ClientShared.HOST_DEVICE_LIST.isEmpty()) {
                long end = System.currentTimeMillis();
                Log.d("FindHostDevicesThread", Long.toString(end - start) + " ms");
                activity.runOnUiThread(() -> {
                    NearbyDeviceAdapter nearbyDeviceAdapter = new NearbyDeviceAdapter(activity, ClientShared.HOST_DEVICE_LIST);
                    activity.deviceListView.setAdapter(nearbyDeviceAdapter);
                    activity.loadNearbyDeviceProgressBar.setVisibility(View.GONE);
                });
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
