package app.arbiterlab.ticandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.interfaces.OnDeviceDetectedListener;
import app.arbiterlab.ticandroid.library.libs.TICPair;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;
import app.arbiterlab.ticandroid.library.models.MusicSpeakerModel;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

public class MainActivity extends AppCompatActivity {

    private TICPair mTICPair;
    private TIC tic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView mainText = (TextView) findViewById(R.id.maintext);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button buttonTest = (Button) findViewById(R.id.button);


        if (!TICUtils.isNeedPermissionsGranted(this)){
            Toast.makeText(this, "ACCESS_COARSE_LOCATION PERMISSION NEED GRANT", Toast.LENGTH_SHORT).show();
        }
        if (!TICUtils.isDeviceSupportBluetooth()) return;
        if (!TICUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
            return;
        }

        mTICPair = new TICPair(this);
        mTICPair.searchDevices(new OnDeviceDetectedListener() {
            @Override
            public void onDetect(BluetoothDevice bluetoothDevice) {
                if (bluetoothDevice.getAddress().equals(bluetoothDevice.getAddress())) {
                    tic = mTICPair.connect(bluetoothDevice, new ConnectionStateListener() {
                        @Override
                        public void onStateChanged(TIC connection, boolean isConnected, String message) {
                            Log.d("bluetoothDeviceDetected", isConnected + ":" + message);
                        }

                        @Override
                        public void onMessage(TIC connection, int bytes, byte[] message) {
                            String output = new String(message);
                            Log.d("output", output);
                            mainText.setText("Message : " + output);
                        }
                    });


                    MusicSpeakerModel musicSpeakerModel = new MusicSpeakerModel(tic);
                    musicSpeakerModel.play();
                }
                Log.d("bluetoothDeviceDetected", "new Device : " + bluetoothDevice.getName() + "/" + bluetoothDevice.getAddress());
            }
        });
        buttonTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendData = editText.getText().toString();
            
                tic.sendText(sendData);
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
            mTICPair = new TICPair(this);
            mTICPair.searchDevices(new OnDeviceDetectedListener() {
                @Override
                public void onDetect(BluetoothDevice bluetoothDevice) {
                    Log.d("bluetoothDeviceDetected", "new Device : " + bluetoothDevice.getName() + "/" + bluetoothDevice.getAddress());
                }
            });
        }
    }
}
