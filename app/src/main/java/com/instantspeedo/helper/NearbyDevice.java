package com.instantspeedo.helper;

import java.io.Serializable;

/**
 * Created by Jialiang on 4/11/15.
 */
public class NearbyDevice implements Serializable{

    private String deviceID;

    public NearbyDevice(String deviceID) {
        this.deviceID = deviceID;
    }

    public NearbyDevice() {
    }


    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceID() {
        return deviceID;
    }
}
