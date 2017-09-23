package app.arbiterlab.ticandroid.library.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Gyeongrok Kim on 2017-09-23.
 */

public interface OnDeviceDetectedListener {
    void onDetect(BluetoothDevice bluetoothDevice);
}
