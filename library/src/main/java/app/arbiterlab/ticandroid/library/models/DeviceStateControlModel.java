package app.arbiterlab.ticandroid.library.models;

import app.arbiterlab.ticandroid.library.datas.TicParam;
import app.arbiterlab.ticandroid.library.interfaces.TicAPI;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by Gyeongrok Kim on 2017-10-10.
 */

public class DeviceStateControlModel {

    private TIC tic;

    public DeviceStateControlModel(TIC tic) {
        this.tic = tic;
    }

    @TicAPI("/api/status")
    public void getDeviceStatus(TicParam... params) {
        tic.work(params);
    }

    @TicAPI("/api/turn/off")
    public void setDeviceOff(TicParam... params) {
        tic.work(params);
    }
}
