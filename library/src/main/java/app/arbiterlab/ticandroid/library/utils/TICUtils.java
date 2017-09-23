package app.arbiterlab.ticandroid.library.utils;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Gyeongrok Kim on 2017-09-23.
 */

public class TICUtils {
    public static boolean isDeviceSupportBluetooth() {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null;
    }

    public static boolean isBluetoothEnabled() {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter.isEnabled();
    }
}
