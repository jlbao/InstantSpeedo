package com.instantspeedo.client;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.instantspeedo.helper.HostDevice;

/**
 * Created by Jialiang on 5/3/15.
 */
public class ConnectHostDeviceThread implements Runnable{

    FindNearbyDevicesActivity activity;

    HostDevice device;

    public ConnectHostDeviceThread(FindNearbyDevicesActivity activity, HostDevice device){
        this.activity = activity;
        this.device = device;
    }

    @Override
    public void run() {
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.VISIBLE);
        });
        // device connection logic
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //----------------end------------
        activity.runOnUiThread(() -> {
            activity.loadNearbyDeviceProgressBar.setVisibility(View.GONE);
            Toast.makeText(activity.getApplicationContext(), device.getDeviceID(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity.getApplicationContext(), SendImageActivity.class);
            activity.startActivity(intent);
        });
    }
}
