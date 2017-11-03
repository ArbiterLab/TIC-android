package app.arbiterlab.ticandroid.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by devkg on 2017-11-04.
 */

public class ConnectedDeviceAdapter extends BaseAdapter {

    private ArrayList<TIC> tics = new ArrayList<>();
    private Context context;

    public ConnectedDeviceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return tics.size();
    }

    @Override
    public TIC getItem(int i) {
        return tics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
