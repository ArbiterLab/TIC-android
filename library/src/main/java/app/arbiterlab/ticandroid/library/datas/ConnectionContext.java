package app.arbiterlab.ticandroid.library.datas;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import app.arbiterlab.ticandroid.library.libs.pair.ConnectAsyncTask;
import app.arbiterlab.ticandroid.library.libs.pair.ConnectThread;
import app.arbiterlab.ticandroid.library.libs.pair.ManageThread;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by Gyeongrok Kim on 2017-09-27.
 */

public class ConnectionContext {
    private String UUID;

    private TIC tic;

    private BluetoothDevice bluetoothDevice;
    private ConnectAsyncTask connectAsyncTask;
    private ConnectThread connectThread;
    private ManageThread manageThread;
    private BluetoothSocket bluetoothSocket;

    public ConnectionContext(String UUID, TIC tic, BluetoothDevice bluetoothDevice) {
        this.UUID = UUID;
        this.bluetoothDevice = bluetoothDevice;
        this.tic = tic;
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

    public TIC getTic() {
        return tic;
    }

    public void setTic(TIC tic) {
        this.tic = tic;
    }
}
