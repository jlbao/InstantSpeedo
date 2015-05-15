package com.instantspeedo.client;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.instantspeedo.R;
import com.instantspeedo.connection.WiFiDirectBroadcastReceiver;
import com.instantspeedo.helper.ClientShared;
import com.instantspeedo.helper.HostDevice;

import java.util.Date;
import java.util.Timer;


public class FindNearbyDevicesActivity extends ActionBarActivity {

    public final static int FIND_INTERVAL_SECONDS = 100;
    public final static String WIFIP2PINFO_LOCK = "LOCK";

    ListView deviceListView;
    ProgressBar loadNearbyDeviceProgressBar;
    Timer timer;
    WiFiDirectBroadcastReceiver receiver;
    IntentFilter filter;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_devices);
        filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel);

        deviceListView = (ListView) findViewById(R.id.deviceListView);
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HostDevice device = ClientShared.HOST_DEVICE_LIST.get(position);
                unregisterReceiver(receiver);
                HandlerThread handlerThread = new HandlerThread("ConnectHostDeviceThread");
                handlerThread.start();
                registerReceiver(receiver, filter, null, new Handler(handlerThread.getLooper()));
                new Thread(new ConnectHostDeviceThread(FindNearbyDevicesActivity.this, manager, channel, receiver, device)).start();
            }
        });
        loadNearbyDeviceProgressBar = (ProgressBar) findViewById(R.id.loadingNearbyDeviceProgressBar);
        Intent intent = getIntent();
        timer = new Timer();
        timer.schedule(new FindHostDevicesThread(this, manager, channel), new Date(), FIND_INTERVAL_SECONDS * 1000);
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

    @Override
    public void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(manager, channel);
        HandlerThread handlerThread = new HandlerThread("FindHostDevicesThread");
        handlerThread.start();
        registerReceiver(receiver, filter, null, new Handler(handlerThread.getLooper()));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
