package com.instantspeedo.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.instantspeedo.R;
import com.instantspeedo.helper.HostDevice;

import java.util.List;

/**
 * Created by Jialiang on 4/11/15.
 */
public class NearbyDeviceAdapter extends ArrayAdapter<HostDevice> {

    public NearbyDeviceAdapter(Context context, List<HostDevice> deviceList) {
        super(context, R.layout.item_device, deviceList);
    }

    @Override
         public View getView(int position, View convertView, ViewGroup parent) {
        HostDevice device = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_device, parent, false);
        }
        TextView idView = (TextView) convertView.findViewById(R.id.deviceID);
        idView.setText(device.getDeviceID());
        return convertView;
    }
}
