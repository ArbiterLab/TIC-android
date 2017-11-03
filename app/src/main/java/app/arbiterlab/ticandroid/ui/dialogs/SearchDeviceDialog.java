package app.arbiterlab.ticandroid.ui.dialogs;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import app.arbiterlab.ticandroid.R;
import app.arbiterlab.ticandroid.ui.adapters.SearchDeviceAdapter;
import app.arbiterlab.ticandroid.library.interfaces.OnDeviceDetectedListener;
import app.arbiterlab.ticandroid.library.libs.TICPair;

/**
 * Created by Gyeongrok Kim on 2017-10-24.
 */

public class SearchDeviceDialog extends Dialog {

    private Context context;
    private OnDeviceSelectedListener onDeviceSelectedListener;

    public SearchDeviceDialog(Context context, OnDeviceSelectedListener onDeviceSelectedListener) {
        super(context);
        this.context = context;
        this.onDeviceSelectedListener = onDeviceSelectedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_search_device);

        final ListView deviceListView = findViewById(R.id.deviceList);
        final SearchDeviceAdapter searchDeviceAdapter = new SearchDeviceAdapter(context);
        deviceListView.setAdapter(searchDeviceAdapter);
        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDeviceSelectedListener.onSelected(searchDeviceAdapter.getItem(position));
                dismiss();
            }
        });

        final TICPair mTICPair = new TICPair(context);
        mTICPair.searchDevices(new OnDeviceDetectedListener() {
            @Override
            public void onDetect(BluetoothDevice bluetoothDevice) {
                searchDeviceAdapter.add(bluetoothDevice);
                searchDeviceAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }

    public interface OnDeviceSelectedListener {
        void onSelected(BluetoothDevice bluetoothDevice);
    }

}
