package com.instantspeedo.host;


import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;

import com.instantspeedo.R;
import com.instantspeedo.helper.HostShared;

import java.util.TimerTask;

/**
 * Created by Jialiang on 5/3/15.
 */
public class ReceiveImageThread extends TimerTask {

    ReceiveImageActivity activity;

    public ReceiveImageThread(ReceiveImageActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        while(true) {
            // this is your socket receive logic
            // Socket socket = ServerSocket.accept();

            activity.runOnUiThread(() -> {
                activity.loadingPanel.setVisibility(View.GONE);
            });

            // you should put your getting file logic
            // then save your file into an directory

            Resources resources = activity.getApplicationContext().getResources();
            Uri imageURI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.my)
                    + '/' + resources.getResourceTypeName(R.drawable.my)
                    + '/' + resources.getResourceEntryName(R.drawable.my));


            // You must get the saved file URI, and to the list (this is to show the image logic)
            HostShared.RECEIVED_IMAGE_URI_LIST.add(imageURI);

            // remove the following sleeping logic, this is for simulation
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // update UI Grid
            activity.runOnUiThread(() -> {
                activity.imageAdapter.notifyDataSetChanged();
                activity.loadingPanel.setVisibility(View.VISIBLE);
            });
        }
    }
}
