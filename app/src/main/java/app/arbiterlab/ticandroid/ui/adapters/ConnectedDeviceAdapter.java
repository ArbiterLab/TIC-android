package app.arbiterlab.ticandroid.ui.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import app.arbiterlab.ticandroid.R;
import app.arbiterlab.ticandroid.databinding.ItemDeviceBinding;
import app.arbiterlab.ticandroid.library.libs.pair.ManageThread;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

import static java.lang.Thread.State.RUNNABLE;

/**
 * Created by devkg on 2017-11-04.
 */

public class ConnectedDeviceAdapter extends BaseAdapter {

    private CopyOnWriteArrayList<TIC> tics = new CopyOnWriteArrayList<>();
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
        final ItemDeviceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_device, null, false);

        final TIC tic = tics.get(i);
        final BluetoothDevice bluetoothDevice = tic.getConnectionContext().getBluetoothDevice();

        binding.deviceName.setText(bluetoothDevice.getName());
        binding.deviceAddress.setText(bluetoothDevice.getAddress());

        return binding.getRoot();
    }

    public void add(TIC tic) {
        tics.add(tic);
    }


    public void remove(TIC tic) {
        tics.remove(tic);
    }

    public void verfiyConnections() {
        for (TIC tic : tics) {
            final ManageThread manageThread = tic.getConnectionContext().getManageThread();
            if (manageThread != null && manageThread.getState() != RUNNABLE) {
                remove(tic);
                Log.d("deviceDeleted", tic.getConnectionContext().getBluetoothDevice().getName());
            }
        }
    }

    public ArrayList<TIC> getAllConnectioncs() {
        ArrayList<TIC> ticsAr = new ArrayList<>();
        ticsAr.addAll(tics);
        return ticsAr;
    }
}
