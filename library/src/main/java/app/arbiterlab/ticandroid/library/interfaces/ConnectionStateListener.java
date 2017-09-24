package app.arbiterlab.ticandroid.library.interfaces;

import app.arbiterlab.ticandroid.library.libs.pair.TICConnection;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public interface ConnectionStateListener {
    void onStateChanged(TICConnection connection, boolean isConnected, String message);
    void onMessage(TICConnection connection, byte[] message);
}
