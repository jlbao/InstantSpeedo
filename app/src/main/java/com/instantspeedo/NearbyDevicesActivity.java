package com.instantspeedo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.instantspeedo.helper.DeviceHelper;
import com.instantspeedo.model.NearbyDevice;

import java.util.LinkedList;
import java.util.List;


public class NearbyDevicesActivity extends ActionBarActivity {

    ListView deviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_devices);
        Intent intent = getIntent();
        List<NearbyDevice> deviceList = DeviceHelper.findNearbyDevices();
        NearbyDeviceAdapter nearbyDeviceAdapter = new NearbyDeviceAdapter(this, deviceList);
        deviceListView = (ListView) findViewById(R.id.deviceListView);
        deviceListView.setAdapter(nearbyDeviceAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nearby_devices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
