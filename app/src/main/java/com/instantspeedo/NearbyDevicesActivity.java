package com.instantspeedo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.instantspeedo.helper.DeviceHelper;
import com.instantspeedo.model.NearbyDevice;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;


public class NearbyDevicesActivity extends ActionBarActivity {

    public final static int FIND_INTERVAL_SECONDS = 10;

    ListView deviceListView;
    ProgressBar loadNearbyDeviceProgressBar;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_devices);
        deviceListView = (ListView) findViewById(R.id.deviceListView);
        loadNearbyDeviceProgressBar = (ProgressBar)findViewById(R.id.loadingNearbyDeviceProgressBar);

        Intent intent = getIntent();
        timer = new Timer();
        timer.schedule(new FindDeviceTask(this), new Date(), FIND_INTERVAL_SECONDS * 1000);
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
