package app.arbiterlab.ticandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import app.arbiterlab.ticandroid.library.interfaces.OnDeviceDetectedListener;
import app.arbiterlab.ticandroid.library.libs.TIC;
import app.arbiterlab.ticandroid.library.libs.TICPair;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

public class MainActivity extends AppCompatActivity {

    private TICPair mTICPair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!TICUtils.isDeviceSupportBluetooth()) return;
        if (!TICUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
            return;
        }

        mTICPair = TIC.init(this);
        mTICPair.searchDevices(new OnDeviceDetectedListener() {
            @Override
            public void onDetect(BluetoothDevice bluetoothDevice) {
                Log.d("bluetoothDeviceDetected", "new Device : " + bluetoothDevice.getName() + "/" + bluetoothDevice.getAddress());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTICPair.detach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            mTICPair = TIC.init(this);
            mTICPair.searchDevices(new OnDeviceDetectedListener() {
                @Override
                public void onDetect(BluetoothDevice bluetoothDevice) {
                    Log.d("bluetoothDeviceDetected", "new Device : " + bluetoothDevice.getName() + "/" + bluetoothDevice.getAddress());
                }
            });
        }
    }
}
