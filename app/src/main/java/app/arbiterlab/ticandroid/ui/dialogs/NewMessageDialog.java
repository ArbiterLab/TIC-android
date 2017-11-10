package app.arbiterlab.ticandroid.ui.dialogs;

import android.app.Dialog;
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

import app.arbiterlab.ticandroid.R;
import app.arbiterlab.ticandroid.databinding.DialogDeviceBinding;
import app.arbiterlab.ticandroid.databinding.DialogNewMessageBinding;
import app.arbiterlab.ticandroid.library.datas.ConnectionContext;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;

/**
 * Created by devkg on 2017-11-05.
 */

public class NewMessageDialog extends Dialog {

    private DialogNewMessageBinding binding;
    private Context context;
    private TIC tic;
    private String textMessage;

    public NewMessageDialog(Context context, TIC tic, String textMessage) {
        super(context);
        this.context = context;
        this.tic = tic;
        this.textMessage = textMessage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_new_message, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final ConnectionContext connectionContext = tic.getConnectionContext();
        final BluetoothDevice bluetoothDevice = connectionContext.getBluetoothDevice();

        binding.deviceName.setText("from " + bluetoothDevice.getName());
        binding.textMessage.setText(textMessage);
        binding.confirmButton.setOnClickListener(view -> dismiss());
    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }

}