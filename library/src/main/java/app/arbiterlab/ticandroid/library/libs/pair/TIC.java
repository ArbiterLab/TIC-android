package app.arbiterlab.ticandroid.library.libs.pair;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.UUID;

import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.datas.TicParam;
import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.interfaces.TicAPI;


/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class TIC {
    private Context context;
    private ConnectionStateListener connectionStateListener;
    private ConnectionContext connectionContext;


    private BluetoothSocket sSocket;

    public TIC(Context context, BluetoothDevice device, ConnectionStateListener connectionStateListener) {
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

    public void work(TicParam... params) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[3];//maybe this number needs to be corrected
        String methodName = e.getMethodName();

        JSONObject resultJSON = new JSONObject();
        try {
            String path = "";
            for (Method m : Class.forName(e.getClassName()).getMethods()){
                if (m.getName().equals(methodName)){
                    path = m.getAnnotation(TicAPI.class).value();
                }
            }

            resultJSON.put("path", path);
            JSONArray paramsJSON = new JSONArray();
            for (TicParam ticParam : params) {
                JSONObject param = new JSONObject();
                param.put(ticParam.getKey(), ticParam.getValue());
                paramsJSON.put(param);
            }

            sendText(paramsJSON.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
            Log.d("resultParam", e1+"");
        }
    }

    public void sendText(String data) {
        data += "\n";
        connectionContext.getManageThread().write(data.getBytes());
    }
}
