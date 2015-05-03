package com.instantspeedo.host;


import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;

import com.instantspeedo.R;
import com.instantspeedo.helper.Shared;

import java.util.LinkedList;
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
        activity.runOnUiThread(()->{
            activity.loadingPanel.setVisibility(View.GONE);
        });

        // receive Images
        if(Shared.receivedImageURIList == null){
            Shared.receivedImageURIList = new LinkedList<Uri>();
        }
        Resources resources = activity.getApplicationContext().getResources();
        Uri imageURI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.my)
                + '/' + resources.getResourceTypeName(R.drawable.my)
                + '/' + resources.getResourceEntryName(R.drawable.my));
        Shared.receivedImageURIList.add(imageURI);

        try {
            Thread.sleep(2000);
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }

        // update UI Grid
        activity.runOnUiThread(() -> {
            activity.imageAdapter.notifyDataSetChanged();
            activity.loadingPanel.setVisibility(View.VISIBLE);
        });
    }
}
