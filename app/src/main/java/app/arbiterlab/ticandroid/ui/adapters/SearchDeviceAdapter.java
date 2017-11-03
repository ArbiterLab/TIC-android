package app.arbiterlab.ticandroid.ui.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.arbiterlab.ticandroid.R;

/**
 * Created by Gyeongrok Kim on 2017-10-26.
 */

public class SearchDeviceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();

    public SearchDeviceAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return bluetoothDevices.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return bluetoothDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_device, null, false);
        final BluetoothDevice bluetoothDevice = bluetoothDevices.get(position);
        final TextView deviceNameTextView = convertView.findViewById(R.id.deviceName);
        final TextView deviceAddressTextView = convertView.findViewById(R.id.deviceAddress);

        deviceNameTextView.setText(bluetoothDevice.getName());
        deviceAddressTextView.setText(bluetoothDevice.getAddress());

        return convertView;
    }

    public void add(BluetoothDevice bluetoothDevice){
        bluetoothDevices.add(bluetoothDevice);
    }
}
