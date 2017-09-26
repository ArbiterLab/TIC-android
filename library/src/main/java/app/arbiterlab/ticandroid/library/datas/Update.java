package app.arbiterlab.ticandroid.library.datas;

/**
 * Created by Gyeongrok Kim on 2017-09-27.
 */

public class Update {
    private ConnectionContext connectionContext;
    private int requestCode;
    private boolean state;
    private Object[] message;

    public Update(ConnectionContext connectionContext, int requestCode, boolean state, Object... message) {
        this.connectionContext = connectionContext;
        this.requestCode = requestCode;
        this.state = state;
        this.message = message;
    }

    public ConnectionContext getConnectionContext() {
        return connectionContext;
    }

    public void setConnectionContext(ConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Object[] getMessage() {
        return message;
    }

    public void setMessage(Object[] message) {
        this.message = message;
    }
}
