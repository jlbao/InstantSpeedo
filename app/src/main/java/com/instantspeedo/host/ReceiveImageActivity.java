package com.instantspeedo.host;

import android.content.Context;
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
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.instantspeedo.R;
import com.instantspeedo.connection.WiFiDirectBroadcastReceiver;


public class ReceiveImageActivity extends ActionBarActivity {

    public GridView gridView;

    public ImageAdapter imageAdapter;

    public ProgressBar loadingPanel;

    WiFiDirectBroadcastReceiver receiver;
    IntentFilter filter;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_image);

        filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel);

        gridView = (GridView) findViewById(R.id.gridview);
        loadingPanel = (ProgressBar) findViewById(R.id.loadingPanel);

        loadingPanel.setVisibility(View.GONE);

        imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener((parent, v, position, id) -> Toast.makeText(ReceiveImageActivity.this, "" + position,
                Toast.LENGTH_SHORT).show());

        // start receive image thread;
        new Thread(new ReceiveImageThread(this, manager, channel)).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wait_image, menu);
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
        HandlerThread handlerThread = new HandlerThread("ReceiveImageThread");
        handlerThread.start();
        registerReceiver(receiver, filter, null, new Handler(handlerThread.getLooper()));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

}
