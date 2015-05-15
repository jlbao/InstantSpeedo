package com.instantspeedo.host;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.instantspeedo.R;
import com.instantspeedo.helper.HostShared;

/**
 * Created by Jialiang on 5/3/15.
 */
public class ImageAdapter extends BaseAdapter {


    private final LayoutInflater mInflater;
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return HostShared.RECEIVED_IMAGE_URI_LIST.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View view = convertView;

        if (view == null) {
            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
            view = mInflater.inflate(R.layout.grid_item, parent, false);
            view.setTag(R.id.picture, view.findViewById(R.id.picture));
        }

        imageView = (ImageView) view.getTag(R.id.picture);
        imageView.setImageURI(HostShared.RECEIVED_IMAGE_URI_LIST.get(position));
        return view;
    }

}
