package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;

import app.arbiterlab.ticandroid.library.libs.Constants;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private TICConnection ticConnection;

    public ConnectThread(TICConnection ticConnection) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        this.mmDevice = ticConnection.getConnectedDevice();
        this.ticConnection = ticConnection;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = ticConnection.getConnectedDevice().createRfcommSocketToServiceRecord(Constants.UUID_OTHER_DEVICE);
        } catch (IOException e) {
        }
        mmSocket = tmp;
    }

    public void run() {


        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            final Intent sendIntent = new Intent("app.arbiterlab.ticandroid." + ticConnection.uniqueUUID);
            sendIntent.putExtra("type", Constants.MESSAGE_STATE_CHANGED);
            sendIntent.putExtra("message", false);
            ticConnection.getContext().sendBroadcast(sendIntent);

            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        ticConnection.setConnectionBluetoothSocket(mmSocket);
        new ManageThread(ticConnection);
    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}