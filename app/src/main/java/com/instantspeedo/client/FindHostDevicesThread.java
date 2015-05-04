package com.instantspeedo.client;

import android.view.View;

import com.instantspeedo.helper.ClientShared;
import com.instantspeedo.helper.DeviceHelper;

import java.util.TimerTask;

/**
 * Created by Jialiang on 4/14/15.
 * Find device thread
 */
public class FindHostDevicesThread extends TimerTask {

    FindNearbyDevicesActivity activity;

    public FindHostDevicesThread(FindNearbyDevicesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        // show the loading bar
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.VISIBLE);
        });
        // this will update the device list in client shared class
        DeviceHelper.findNearbyHostDevices();
        activity.runOnUiThread(() -> {
            NearbyDeviceAdapter nearbyDeviceAdapter = new NearbyDeviceAdapter(activity, ClientShared.HOST_DEVICE_LIST);
            activity.deviceListView.setAdapter(nearbyDeviceAdapter);
            activity.loadNearbyDeviceProgressBar.setVisibility(View.GONE);
        });
    }
}
