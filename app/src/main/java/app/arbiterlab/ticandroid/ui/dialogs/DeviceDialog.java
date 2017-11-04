package app.arbiterlab.ticandroid.ui.dialogs;

import android.app.Dialog;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import app.arbiterlab.ticandroid.R;
import app.arbiterlab.ticandroid.databinding.DialogDeviceBinding;
import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;
import app.arbiterlab.ticandroid.ui.adapters.SearchDeviceAdapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by devkg on 2017-11-04.
 */

public class DeviceDialog extends Dialog {

    private DialogDeviceBinding binding;
    private Context context;
    private TIC tic;

    public DeviceDialog(Context context, TIC tic) {
        super(context);
        this.context = context;
        this.tic = tic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_device, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final ConnectionContext connectionContext = tic.getConnectionContext();
        final BluetoothDevice bluetoothDevice = connectionContext.getBluetoothDevice();

        binding.deviceName.setText(bluetoothDevice.getName());
        binding.deviceAddress.setText(bluetoothDevice.getAddress());
        binding.deviceUUID.setText(bluetoothDevice.getUuids().toString());
        binding.sendButton.setOnClickListener(view -> new RequestAPIDialog(context, tic).show());
    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }

}
