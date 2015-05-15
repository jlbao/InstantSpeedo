package com.instantspeedo.client;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Jialiang on 5/3/15.
 */
public class SendImageThread implements Runnable {

    File file;

    SendImageActivity activity;

    String hostAddress;

    public SendImageThread(SendImageActivity activity, File file, String hostAddress) {
        this.activity = activity;
        this.file = file;
        this.hostAddress = hostAddress;
    }

    @Override
    public void run() {
        activity.runOnUiThread(() -> {
            activity.loadingPanel.setVisibility(View.VISIBLE);
        });

        // Use socket to send file to host device
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Socket socketToHost = new Socket();
        try {
            socketToHost.connect(new InetSocketAddress(hostAddress, 8888));
            OutputStream outputStream = socketToHost.getOutputStream();
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            bis.read(buffer, 0, buffer.length);
            outputStream.write(buffer, 0, buffer.length);
//            outputStream.flush();
//            outputStream.write("Hello Master".getBytes());
            outputStream.flush();
            outputStream.close();
            socketToHost.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------
        Log.d("send message", "successful");
        activity.runOnUiThread(() -> {
            activity.loadingPanel.setVisibility(View.GONE);
            Toast.makeText(activity.getApplicationContext(), "Successfully sent " + file.getPath(), Toast.LENGTH_SHORT);
        });
    }
}
