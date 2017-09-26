package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.datas.Update;
import app.arbiterlab.ticandroid.library.interfaces.OnUpdate;
import app.arbiterlab.ticandroid.library.libs.Constants;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class ManageThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final OnUpdate onUpdate;
    private ConnectionContext connectionContext;

    public ManageThread(ConnectionContext connectionContext, OnUpdate onUpdate) {
        this.connectionContext = connectionContext;
        this.onUpdate = onUpdate;

        mmSocket = connectionContext.getBluetoothSocket();
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = mmSocket.getInputStream();
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                onUpdate.OnUpdate(new Update(connectionContext, Constants.RESULT_MESSAGE, bytes));
            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}