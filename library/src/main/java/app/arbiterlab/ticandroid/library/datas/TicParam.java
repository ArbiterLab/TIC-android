package app.arbiterlab.ticandroid.library.datas;

/**
 * Created by Gyeongrok Kim on 2017-10-10.
 */

public class TicParam {
    private String key;
    private String value;

    public TicParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
