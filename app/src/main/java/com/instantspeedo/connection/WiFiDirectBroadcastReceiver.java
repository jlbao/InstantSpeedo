package com.instantspeedo.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

import com.instantspeedo.helper.ClientShared;
import com.instantspeedo.helper.HostDevice;


/**
 * Created by Dongyun on 4/28/2015.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    /**
     * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
     */

    private final static String WIFI_P2P_INFO_LOCK = "LOCK";
    boolean connected;
    WifiP2pInfo wifiP2pInfo;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel
    ) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                synchronized (WIFI_P2P_INFO_LOCK) {
                    wifiP2pInfo = info;
                }
            }
        };

        this.connected = false;

        this.peerListListener = peerList -> {
//            Clear the host list first
            ClientShared.HOST_DEVICE_LIST.clear();

            for (WifiP2pDevice device :
                    peerList.getDeviceList()) {
                if (device.isGroupOwner()) {
                    ClientShared.HOST_DEVICE_LIST.add(new HostDevice(device.deviceName, device));
                }
            }
        };
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (manager != null) {
                manager.requestPeers(channel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            if (manager == null) {
                return;
            }

            NetworkInfo networkInfo = intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                manager.requestConnectionInfo(channel, connectionInfoListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
        }
    }

    public WifiP2pInfo getWiFiP2pInfo() {
        synchronized (WIFI_P2P_INFO_LOCK) {
            return this.wifiP2pInfo;
        }
    }
}
