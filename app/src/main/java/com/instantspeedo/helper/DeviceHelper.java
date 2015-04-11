package com.instantspeedo.helper;

import com.instantspeedo.model.NearbyDevice;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jialiang on 4/11/15.
 */
public class DeviceHelper {

    public static List<NearbyDevice> findNearbyDevices(){
        // mock some data
        List<NearbyDevice> deviceList = new LinkedList<NearbyDevice>();
        for(int i = 0; i < 10; i++){
            deviceList.add(new NearbyDevice("hello" + i));
        }
        return deviceList;
    }
}
