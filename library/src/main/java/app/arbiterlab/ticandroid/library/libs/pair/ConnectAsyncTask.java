package app.arbiterlab.ticandroid.library.libs.pair;

import android.os.AsyncTask;

import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.datas.Update;
import app.arbiterlab.ticandroid.library.interfaces.ConnectionStateListener;
import app.arbiterlab.ticandroid.library.interfaces.OnUpdate;
import app.arbiterlab.ticandroid.library.libs.Constants;

/**
 * Created by Gyeongrok Kim on 2017-09-27.
 */

public class ConnectAsyncTask extends AsyncTask<Integer, Object, Integer> {

    private ConnectionContext connectionContext;
    private ConnectionStateListener connectionStateListener;

    public ConnectAsyncTask(ConnectionContext connectionContext, ConnectionStateListener connectionStateListener) {
        this.connectionContext = connectionContext;
        this.connectionStateListener = connectionStateListener;
        this.connectionContext.setConnectAsyncTask(this);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        final ConnectThread connectThread = new ConnectThread(connectionContext, new OnUpdate() {
            @Override
            public void OnUpdate(Update update) {
                publishProgress(update);
            }
        });
        connectThread.start();
        connectionContext.setConnectThread(connectThread);
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    // 0 - request code, 1 - connectioncontext, 2 ~ optional
    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        final Update update = (Update) values[0];
        if (update == null) return;

        connectionContext = update.getConnectionContext();
        connectionContext.getTic().setConnectionContext(connectionContext);

        switch (update.getRequestCode()) {
            case Constants.RESULT_STATECHANGED:
                connectionStateListener.onStateChanged(update.getConnectionContext().getTic(),
                        update.isState(), (String) update.getMessage()[0]);
                break;
            case Constants.RESULT_MESSAGE:
                connectionStateListener.onMessage(update.getConnectionContext().getTic(),
                        (int) update.getMessage()[0], (byte[]) update.getMessage()[1]);
                break;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
