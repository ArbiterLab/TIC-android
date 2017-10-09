package app.arbiterlab.ticandroid.library.models;

import app.arbiterlab.ticandroid.library.datas.TicParam;
import app.arbiterlab.ticandroid.library.interfaces.TicAPI;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by Gyeongrok Kim on 2017-10-10.
 */

public class MusicSpeakerModel {

    private TIC tic;

    public MusicSpeakerModel(TIC tic) {
        this.tic = tic;
    }

    @TicAPI("/api/play")
    public void play(TicParam... params){
        tic.work(params);
    }

    @TicAPI("/api/stop")
    public void stop(TicParam... params){
        tic.work(params);
    }
}
