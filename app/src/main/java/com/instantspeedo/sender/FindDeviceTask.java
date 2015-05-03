package com.instantspeedo.sender;

import android.view.View;

import com.instantspeedo.helper.DeviceHelper;
import com.instantspeedo.helper.Shared;

import java.util.TimerTask;

/**
 * Created by Jialiang on 4/14/15.
 * Find device thread
 */
public class FindDeviceTask extends TimerTask {

    FindNearbyDevicesActivity activity;

    public FindDeviceTask(FindNearbyDevicesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        // show the loading bar
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.VISIBLE);
        });
        DeviceHelper.findNearbyDevices();
        activity.runOnUiThread(() -> {
            NearbyDeviceAdapter nearbyDeviceAdapter = new NearbyDeviceAdapter(activity, Shared.deviceList);
            activity.deviceListView.setAdapter(nearbyDeviceAdapter);
            activity.loadNearbyDeviceProgressBar.setVisibility(View.GONE);
        });
    }
}
