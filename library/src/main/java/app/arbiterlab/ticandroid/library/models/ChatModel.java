package app.arbiterlab.ticandroid.library.models;

import app.arbiterlab.ticandroid.library.datas.TicParam;
import app.arbiterlab.ticandroid.library.interfaces.TicAPI;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by Gyeongrok Kim on 2017-10-10.
 */

public class ChatModel {

    private TIC tic;

    public ChatModel(TIC tic) {
        this.tic = tic;
    }

    @TicAPI("/api/sendMessage")
    public void sendMessage(TicParam... params){
        tic.work(params);
    }

    @TicAPI("/api/deleteRoom")
    public void deleteRoom(TicParam... params){
        tic.work(params);
    }

    @TicAPI("/api/createRoom")
    public void createRoom(TicParam... params){
        tic.work(params);
    }
}
