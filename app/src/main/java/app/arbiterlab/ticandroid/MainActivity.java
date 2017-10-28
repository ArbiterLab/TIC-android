package app.arbiterlab.ticandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.libs.TICPair;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

public class MainActivity extends AppCompatActivity {

    private TICPair mTICPair;
    private TIC tic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView mainText = findViewById(R.id.maintext);
        final EditText editText = findViewById(R.id.editText);
        final Button buttonTest = findViewById(R.id.button);

        if (!TICUtils.isNeedPermissionsGranted(this)) {
            Toast.makeText(this, "PLEASE TURN ON APPS PERMISSION", Toast.LENGTH_LONG).show();
            finish();
        }

        if (!TICUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
            return;
        }

        startLogic();

        buttonTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendData = editText.getText().toString();
                tic.sendText(sendData);
            }
        });
    }

    public void startLogic() {
        SearchDeviceDialog searchDeviceDialog = new SearchDeviceDialog(this,
                new SearchDeviceDialog.OnDeviceSelectedListener() {
                    @Override
                    public void onSelected(final BluetoothDevice bluetoothDevice) {
                        final TICPair ticPair = new TICPair(MainActivity.this);
                        tic = ticPair.open(bluetoothDevice, new ConnectionStateListener() {
                            @Override
                            public void onStateChanged(TIC connection, boolean isConnected, String message) {
                                if (isConnected) {
                                    Toast.makeText(MainActivity.this, "CONNECTED SUCCESSFUL WITH :" + bluetoothDevice.getName(), Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "CONNECTED FAILED CAUSE : " + message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onMessage(TIC connection, int bytes, byte[] message) {

                            }
                        });
                        Toast.makeText(MainActivity.this, "SELECTED DEVICE : " + bluetoothDevice.getName(), Toast.LENGTH_LONG).show();
                    }
                });
        searchDeviceDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTICPair != null)
            mTICPair.detach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            startLogic();
        }
    }
}
