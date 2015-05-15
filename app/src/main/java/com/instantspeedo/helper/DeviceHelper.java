package com.instantspeedo.helper;

import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by Jialiang on 4/11/15.
 */
public class DeviceHelper {

    /**
     * find nearby host devices
     */
    public static void findNearbyHostDevices(WifiP2pManager manager, WifiP2pManager.Channel channel) {
        // do not forget to clean the list first

        // do all find host logic here

        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
        //---------------------------
    }
}
