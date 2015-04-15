package com.instantspeedo.helper;

import com.instantspeedo.model.NearbyDevice;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jialiang on 4/11/15.
 */
public class DeviceHelper {

    public static List<NearbyDevice> findNearbyDevices(){
        // mock some data
        List<NearbyDevice> deviceList = new LinkedList<NearbyDevice>();
        Random rand = new Random();
        int times = rand.nextInt(20);
        for(int i = 0; i < times; i++){
            deviceList.add(new NearbyDevice("hello" + i));
        }
        return deviceList;
    }
}
