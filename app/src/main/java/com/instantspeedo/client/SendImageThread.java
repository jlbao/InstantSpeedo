package com.instantspeedo.client;

import android.view.View;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Jialiang on 5/3/15.
 */
public class SendImageThread implements Runnable {

    File file;

    SendImageActivity activity;

    public SendImageThread(SendImageActivity activity, File file) {
        this.activity = activity;
        this.file = file;
    }

    @Override
    public void run() {
        activity.runOnUiThread(() -> {
            activity.loadingPanel.setVisibility(View.VISIBLE);
        });

        // Use socket to send file to host device
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ---------------------
        activity.runOnUiThread(() -> {
            activity.loadingPanel.setVisibility(View.GONE);
            Toast.makeText(activity.getApplicationContext(), "Successfully sent " + file.getPath(), Toast.LENGTH_SHORT);
        });
    }
}
