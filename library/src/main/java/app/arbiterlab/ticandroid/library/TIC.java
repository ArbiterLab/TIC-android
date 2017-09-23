package app.arbiterlab.ticandroid.library;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import app.arbiterlab.ticandroid.library.exceptions.BluetoothNotEnabledException;
import app.arbiterlab.ticandroid.library.exceptions.DeviceNotSupportBluetoothException;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

/**
 * Created by Gyeongrok Kim on 2017-09-19.
 */

public class TIC {

    private BluetoothAdapter mBluetoothAdapter;
    private Context context;

    public TIC(Context context) {

    }

    public TIC init() throws DeviceNotSupportBluetoothException, BluetoothNotEnabledException {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!TICUtils.isDeviceSupportBluetooth()) {
            throw new DeviceNotSupportBluetoothException();
        }
        if (!TICUtils.isBluetoothEnabled()) {
            throw new BluetoothNotEnabledException();
        }
        return this;
    }

}
