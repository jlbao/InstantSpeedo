package com.instantspeedo.helper;

import android.net.wifi.p2p.WifiP2pDevice;

import java.io.Serializable;

/**
 * Created by Jialiang on 4/11/15.
 */
public class HostDevice implements Serializable {

    // PUT ALL WIFI RELATED STUFF HERE

    private String deviceID;
    private WifiP2pDevice wifiP2pDevice;

    public HostDevice(String deviceID, WifiP2pDevice wifiP2pDevice) {
        this.deviceID = deviceID;
        this.wifiP2pDevice = wifiP2pDevice;
    }

    public HostDevice() {
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public WifiP2pDevice getWifiP2pDevice() {
        return wifiP2pDevice;
    }

    public void setWifiP2pDevice(WifiP2pDevice wifiP2pDevice) {
        this.wifiP2pDevice = wifiP2pDevice;
    }
}
