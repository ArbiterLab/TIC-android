package app.arbiterlab.ticandroid;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.arbiterlab.ticandroid.library.TIC;
import app.arbiterlab.ticandroid.library.utils.TICUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!TICUtils.isBluetoothEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
        }

        try {
            TIC tic = new TIC(this).init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
