package com.instantspeedo.host;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.instantspeedo.helper.HostShared;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by Jialiang on 5/3/15.
 */
public class ReceiveImageThread extends TimerTask {

    ReceiveImageActivity activity;

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    ServerSocket serverSocket;

    public ReceiveImageThread(ReceiveImageActivity activity, WifiP2pManager manager, WifiP2pManager.Channel channel) {
        this.activity = activity;
        this.manager = manager;
        this.channel = channel;
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        manager.createGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });


        while (true) {
            // this is your socket receive logic

            try {
                Socket client = serverSocket.accept();

                activity.runOnUiThread(() -> activity.loadingPanel.setVisibility(View.VISIBLE));

                Calendar calendar = Calendar.getInstance();
                String name = client.getInetAddress().getHostAddress() + "_" + Integer.toString(calendar.get(calendar.YEAR)) + Integer.toString(calendar.get(calendar.HOUR_OF_DAY)) + Integer.toString(calendar.get(calendar.MINUTE)) + Integer.toString(calendar.get(calendar.SECOND));

                String filename = "";
                long start = System.currentTimeMillis();

                // you should put your getting file logic
                // then save your file into an directory

                try {
                    InputStream inputStream = client.getInputStream();

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    filename = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, name, null);
                    bitmap.recycle();
                    System.gc();
                    inputStream.close();
                    client.close();

                    long end = System.currentTimeMillis();
                    long elapsed = end - start;
                    Log.d("ReceiveImageThread", "Time elapsed: " + elapsed + "ms ");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Uri imageURI = Uri.parse(filename);

                // You must get the saved file URI, and to the list (this is to show the image logic)
                HostShared.RECEIVED_IMAGE_URI_LIST.add(imageURI);

                // update UI Grid
                activity.runOnUiThread(() -> {
                    activity.loadingPanel.setVisibility(View.GONE);
                    activity.imageAdapter.notifyDataSetChanged();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
