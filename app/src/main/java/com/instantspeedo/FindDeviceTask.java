package com.instantspeedo;

import android.view.View;

import com.instantspeedo.helper.DeviceHelper;
import com.instantspeedo.model.NearbyDevice;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by Jialiang on 4/14/15.
 * Find device thread
 */
public class FindDeviceTask extends TimerTask {

    NearbyDevicesActivity activity;

    public FindDeviceTask(NearbyDevicesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        // show the loading bar
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.VISIBLE);
        });
        List<NearbyDevice> deviceList = DeviceHelper.findNearbyDevices();
        try {
            // mock the wait
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Once devices are found, update the UI to display the devices
        activity.runOnUiThread(() -> {
            NearbyDeviceAdapter nearbyDeviceAdapter = new NearbyDeviceAdapter(activity, deviceList);
            activity.deviceListView.setAdapter(nearbyDeviceAdapter);
            activity.loadNearbyDeviceProgressBar.setVisibility(View.GONE);
        });
    }
}
