package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.libs.Constants;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class TICConnection {

    private Context context;

    public ConnectionStateListener connectionStateListener;

    // for tic connection distinction;
    public String uniqueUUID;

    private BluetoothDevice connectedDevice;
    private BluetoothSocket connectionBluetoothSocket;

    private BroadcastReceiver broadcastReceiver;

    private ConnectThread connectThread;
    private ManageThread manageThread;


    public TICConnection(Context context, BluetoothDevice connectedDevice, ConnectionStateListener connectionStateListener) {
        if (connectedDevice == null || connectionStateListener == null)
            throw new NullPointerException();
        this.uniqueUUID = UUID.randomUUID().toString();

        this.context = context;
        this.connectedDevice = connectedDevice;
        this.registerFetcher();
        connectThread = new ConnectThread(this);
    }

    private void registerFetcher() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("app.arbiterlab.ticandroid." + uniqueUUID);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String type = intent.getStringExtra("type");
                if (type.isEmpty() || connectionStateListener == null) return;
                if (type.equals(Constants.MESSAGE_STATE_CHANGED)) {
                    connectionStateListener.onStateChanged(TICConnection.this, intent.getBooleanExtra("state", false)
                            , intent.getStringExtra("message"));
                }
                if (type.equals(Constants.MESSAGE_ON_TEXT)) {
                    connectionStateListener.onMessage(TICConnection.this, intent.getByteArrayExtra("message"));
                }
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public void cancel() {
        try {
            connectionBluetoothSocket.close();
        } catch (IOException e) {

        }
        context.unregisterReceiver(broadcastReceiver);
        connectionStateListener = null;
    }

    protected Context getContext() {
        return context;
    }

    public BluetoothDevice getConnectedDevice() {
        return connectedDevice;
    }

    protected BluetoothSocket getConnectionBluetoothSocket() {
        return connectionBluetoothSocket;
    }

    protected void setConnectionBluetoothSocket(BluetoothSocket connectionBluetoothSocket) {
        this.connectionBluetoothSocket = connectionBluetoothSocket;
    }

    protected void setManageThread(ManageThread manageThread) {
        this.manageThread = manageThread;
    }
}
