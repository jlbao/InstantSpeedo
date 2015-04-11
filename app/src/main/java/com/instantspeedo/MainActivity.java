package com.instantspeedo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private final String TAG = "My Log";

    public final static String MESSAGE_EXTRA = "nearbyDevices";

    public Button becomeHostButton;

    public Button findNearbyDevicesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        becomeHostButton = (Button) findViewById(R.id.becomeHostButton);
        findNearbyDevicesButton = (Button) findViewById(R.id.findNearByDevicesButton);
        becomeHostButton.setOnClickListener((View v) -> {


        });

        findNearbyDevicesButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, NearbyDevicesActivity.class);
            startActivity(intent);
        });

        Log.i(TAG, "on start");
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
