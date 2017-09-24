package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.arbiterlab.ticandroid.library.libs.Constants;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

class ManageThread extends Thread {
    private TICConnection ticConnection;
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private Intent sendIntent;

    public ManageThread(TICConnection ticConnection) {
        sendIntent = new Intent("app.arbiterlab.ticandroid." + ticConnection.uniqueUUID);
        this.ticConnection = ticConnection;
        mmSocket = ticConnection.getConnectionBluetoothSocket();
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


        ticConnection.setManageThread(this);
        if (tmpIn !=null && tmpOut != null){
            sendIntent.putExtra("message", true);
        }else{
            sendIntent.putExtra("message", false);
        }
        sendIntent.putExtra("type", Constants.MESSAGE_STATE_CHANGED);
        ticConnection.getContext().sendBroadcast(sendIntent);
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

                sendIntent = new Intent("app.arbiterlab.ticandroid." + ticConnection.uniqueUUID);
                sendIntent.putExtra("type", Constants.MESSAGE_STATE_CHANGED);
                sendIntent.putExtra("message", bytes);
                ticConnection.getContext().sendBroadcast(sendIntent);
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