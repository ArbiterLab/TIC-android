package app.arbiterlab.ticandroid.library.interfaces;

import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public interface ConnectionStateListener {
    void onStateChanged(TIC connection, boolean isConnected, String message);

    void onMessage(TIC connection, int bytes, byte[] message);
}