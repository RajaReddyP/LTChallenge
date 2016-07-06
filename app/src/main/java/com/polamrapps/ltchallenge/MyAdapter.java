package com.polamrapps.ltchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Rajareddy on 06/07/16.
 */
public class MyAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private ArrayList<Device> devices;

    public MyAdapter(ArrayList<Device> list) {
        devices = list;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_layout, parent, false);
        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        Device device = devices.get(position);
        holder.deviceNameTV.setText(device.getName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
