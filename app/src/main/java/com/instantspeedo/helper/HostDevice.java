package com.instantspeedo.helper;

import java.io.Serializable;

/**
 * Created by Jialiang on 4/11/15.
 */
public class HostDevice implements Serializable{

    // PUT ALL WIFI RELATED STUFF HERE

    private String deviceID;

    public HostDevice(String deviceID) {
        this.deviceID = deviceID;
    }

    public HostDevice() {
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceID() {
        return deviceID;
    }
}
