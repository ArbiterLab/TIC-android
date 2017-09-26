package app.arbiterlab.ticandroid.library.datas;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import app.arbiterlab.ticandroid.library.libs.pair.ConnectAsyncTask;
import app.arbiterlab.ticandroid.library.libs.pair.ConnectThread;
import app.arbiterlab.ticandroid.library.libs.pair.ManageThread;
import app.arbiterlab.ticandroid.library.libs.pair.TICConnection;

/**
 * Created by Gyeongrok Kim on 2017-09-27.
 */

public class ConnectionContext {
    private String UUID;

    private TICConnection ticConnection;

    private BluetoothDevice bluetoothDevice;
    private ConnectAsyncTask connectAsyncTask;
    private ConnectThread connectThread;
    private ManageThread manageThread;
    private BluetoothSocket bluetoothSocket;

    public ConnectionContext(String UUID, TICConnection ticConnection, BluetoothDevice bluetoothDevice) {
        this.UUID = UUID;
        this.bluetoothDevice = bluetoothDevice;
        this.ticConnection = ticConnection;
    }

    public String getUUID() {
        return UUID;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
    }

    public ConnectAsyncTask getConnectAsyncTask() {
        return connectAsyncTask;
    }

    public void setConnectAsyncTask(ConnectAsyncTask connectAsyncTask) {
        this.connectAsyncTask = connectAsyncTask;
    }

    public ConnectThread getConnectThread() {
        return connectThread;
    }

    public void setConnectThread(ConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    public ManageThread getManageThread() {
        return manageThread;
    }

    public void setManageThread(ManageThread manageThread) {
        this.manageThread = manageThread;
    }

    public TICConnection getTicConnection() {
        return ticConnection;
    }

    public void setTicConnection(TICConnection ticConnection) {
        this.ticConnection = ticConnection;
    }
}
