package app.arbiterlab.ticandroid.library.libs;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import app.arbiterlab.ticandroid.library.exceptions.BluetoothNotEnabledException;
import app.arbiterlab.ticandroid.library.exceptions.DeviceNotSupportBluetoothException;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

/**
 * Created by Gyeongrok Kim on 2017-09-19.
 */

public class TIC {


    public static TICPair init(Context context) throws DeviceNotSupportBluetoothException, BluetoothNotEnabledException {
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!TICUtils.isDeviceSupportBluetooth()) {
            throw new DeviceNotSupportBluetoothException();
        }
        if (!TICUtils.isBluetoothEnabled()) {
            throw new BluetoothNotEnabledException();
        }
        return new TICPair(context, mBluetoothAdapter);
    }

}
