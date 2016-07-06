package com.polamrapps.ltchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rajareddy on 06/07/16.
 */
public class DeviceViewHolder extends RecyclerView.ViewHolder {

    public TextView deviceNameTV;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        deviceNameTV = (TextView) itemView.findViewById(R.id.deviceName);
    }
}
