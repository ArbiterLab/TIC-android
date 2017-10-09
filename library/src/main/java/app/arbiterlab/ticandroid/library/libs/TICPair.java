package app.arbiterlab.ticandroid.library.libs;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.Set;

import app.arbiterlab.ticandroid.library.exceptions.BluetoothNotEnabledException;
import app.arbiterlab.ticandroid.library.exceptions.DeviceNotSupportBluetoothException;
import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.interfaces.OnDeviceDetectedListener;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

/**
 * Created by Gyeongrok Kim on 2017-09-23.
 */

public class TICPair {

    private BroadcastReceiver currentSearchReceiver;
    private BluetoothAdapter bluetoothAdapter;
    private Context context;

    private ArrayList<TIC> tics = new ArrayList<>();

    public TICPair(Context context) throws DeviceNotSupportBluetoothException, BluetoothNotEnabledException {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!TICUtils.isDeviceSupportBluetooth()) {
            throw new DeviceNotSupportBluetoothException();
        }
        if (!TICUtils.isBluetoothEnabled()) {
            throw new BluetoothNotEnabledException();
        }

        if (bluetoothAdapter == null)
            throw new NullPointerException("error on bluetoothAdapter initialized");

        this.context = context;
    }

    public Set<BluetoothDevice> getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        return pairedDevices;
    }

    public void searchDevices(final OnDeviceDetectedListener onDeviceDetectedListener) {
        // Create a BroadcastReceiver for ACTION_FOUND
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    onDeviceDetectedListener.onDetect(device);
                }
            }
        };

        bluetoothAdapter.cancelDiscovery();
        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }

    public void detach() {
        if (currentSearchReceiver != null)
            context.unregisterReceiver(currentSearchReceiver);
        for (TIC tic : tics) {
            try {
//                tic.cancel();
            } catch (Exception e) {
            }
        }
    }

    public TIC connect(BluetoothDevice device, ConnectionStateListener connectionStateListener) {
        // Cancel discovery because it will slow down the connection
        bluetoothAdapter.cancelDiscovery();

        final TIC tic = new TIC(context, device, connectionStateListener);
        tics.add(tic);
        return tic;
    }

}
