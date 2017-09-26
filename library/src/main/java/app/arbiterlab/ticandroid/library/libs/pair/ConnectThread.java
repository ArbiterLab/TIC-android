package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.interfaces.OnUpdate;
import app.arbiterlab.ticandroid.library.libs.Constants;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class ConnectThread extends Thread {

    private ConnectionContext connectionContext;
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final OnUpdate onUpdate;

    public ConnectThread(final ConnectionContext connectionContext, final OnUpdate onUpdate) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;

        this.onUpdate = onUpdate;
        this.connectionContext = connectionContext;
        this.mmDevice = connectionContext.getBluetoothDevice();

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(Constants.UUID_OTHER_DEVICE);
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
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) {

            }
            return;
        }
        // Do work to manage the connection (in a separate thread)
        connectionContext.setBluetoothSocket(mmSocket);

        ManageThread manageThread = new ManageThread(connectionContext);
        manageThread.start();

        connectionContext.setManageThread(manageThread);
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