package app.arbiterlab.ticandroid;

import app.arbiterlab.ticandroid.library.datas.TicParam;
import app.arbiterlab.ticandroid.library.interfaces.TicAPI;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by Gyeongrok Kim on 2017-10-10.
 */

public class CustomModel {

    private TIC tic;

    public CustomModel(TIC tic){
        this.tic = tic;
    }

    @TicAPI("/api~/custom1")
    public void custom1(TicParam... params){
        tic.work(params);
    }

    @TicAPI("/api~/custom2")
    public void custom2(TicParam... params){
        tic.work(params);
    }
}
