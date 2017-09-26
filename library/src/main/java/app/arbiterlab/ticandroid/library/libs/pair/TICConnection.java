package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;

import java.util.UUID;

import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class TICConnection {

    private Context context;
    private ConnectionStateListener connectionStateListener;
    private ConnectionContext connectionContext;

    public TICConnection(Context context, BluetoothDevice device, ConnectionStateListener connectionStateListener) {
        if (device == null || connectionStateListener == null)
            throw new NullPointerException();

        // import connect thread, library connection
        connectionContext = new ConnectionContext(UUID.randomUUID().toString(), this, device);

        // connect task execute
        ConnectAsyncTask connectAsyncTask = new ConnectAsyncTask(connectionContext, connectionStateListener);
        connectAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
    }

    protected Context getContext() {
        return context;
    }

    protected ConnectionContext getConnectionContext() {
        return connectionContext;
    }

    protected void setConnectionContext(ConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }
}
