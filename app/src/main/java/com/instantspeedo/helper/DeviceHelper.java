package com.instantspeedo.helper;

import java.util.Random;

/**
 * Created by Jialiang on 4/11/15.
 */
public class DeviceHelper {

    /**
     * find nearby host devices
     */
    public static void findNearbyHostDevices() {
        // do not forget to clean the list first
        ClientShared.HOST_DEVICE_LIST.clear();

        // do all find host logic here
        Random rand = new Random();
        int times = rand.nextInt(20);
        for (int i = 0; i < times; i++) {
            ClientShared.HOST_DEVICE_LIST.add(new HostDevice("hello world " + i));
        }
        //---------------------------
    }
}
