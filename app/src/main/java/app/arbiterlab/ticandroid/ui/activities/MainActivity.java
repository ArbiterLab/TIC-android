package app.arbiterlab.ticandroid.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import app.arbiterlab.ticandroid.R;
import app.arbiterlab.ticandroid.databinding.ActivityMainBinding;
import app.arbiterlab.ticandroid.databinding.ItemTextBinding;
import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.libs.TICPair;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;
import app.arbiterlab.ticandroid.library.utils.TICUtils;
import app.arbiterlab.ticandroid.ui.adapters.ConnectedDeviceAdapter;
import app.arbiterlab.ticandroid.ui.dialogs.DeviceDialog;
import app.arbiterlab.ticandroid.ui.dialogs.NewMessageDialog;
import app.arbiterlab.ticandroid.ui.dialogs.SearchDeviceDialog;

public class MainActivity extends AppCompatActivity {

    private TICPair ticPair;
    private ItemTextBinding footerTextBinding;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        binding.toolbarTitle.setText("Connected Devices");

        if (!TICUtils.isNeedPermissionsGranted(getApplicationContext())) {
            Toast.makeText(this, "PLEASE TURN ON APPS PERMISSION", Toast.LENGTH_LONG).show();
            finish();
        }

        if (!TICUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
        } else {
            ticPair = new TICPair(MainActivity.this);
        }


        final ConnectedDeviceAdapter connectedDeviceAdapter = new ConnectedDeviceAdapter(this);
        binding.connectedDeviceList.setAdapter(connectedDeviceAdapter);

        footerTextBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_text, null, false);
        footerTextBinding.textView.setText("START NEW CONNECTION");
        footerTextBinding.getRoot().setOnClickListener(view ->
                new SearchDeviceDialog(MainActivity.this, bluetoothDevice -> {
                    final TextView monitorTextView = footerTextBinding.textView;
                    monitorTextView.setText("Connecting with " + bluetoothDevice.getName() + " ...");

                    footerTextBinding.getRoot().setEnabled(false);
                    ticPair.open(bluetoothDevice, new ConnectionStateListener() {
                        @Override
                        public void onStateChanged(TIC connection, boolean isConnected, String message) {
                            footerTextBinding.getRoot().setEnabled(true);
                            if (isConnected) {
                                connectedDeviceAdapter.add(connection);
                                connectedDeviceAdapter.verfiyConnections();
                                connectedDeviceAdapter.notifyDataSetChanged();

                                monitorTextView.setText("START NEW CONNECTION");
                                Toast.makeText(MainActivity.this, "CONNECTED SUCCESSFUL WITH :" + bluetoothDevice.getName(), Toast.LENGTH_LONG).show();
                            } else {
                                monitorTextView.setText("CONNECT ABORTED : " + message);
                                Toast.makeText(MainActivity.this, "CONNECTED FAILED CAUSE : " + message, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onMessage(TIC connection, int bytes, byte[] message) {
                            new NewMessageDialog(MainActivity.this, connection, new String(message)).show();
                        }
                    });
                }).show());
        binding.connectedDeviceList.addFooterView(footerTextBinding.getRoot());
        binding.connectedDeviceList.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                    final DeviceDialog deviceDialog = new DeviceDialog(MainActivity.this, connectedDeviceAdapter.getItem(i));
                    deviceDialog.show();
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ticPair != null)
            ticPair.detach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                ticPair = new TICPair(MainActivity.this);
            } else {
                Toast.makeText(MainActivity.this, "BLUETOOTH EN", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
