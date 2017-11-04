package app.arbiterlab.ticandroid.ui.dialogs;

import android.app.Dialog;
import android.app.DownloadManager;
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
import app.arbiterlab.ticandroid.databinding.DialogRequestApiBinding;
import app.arbiterlab.ticandroid.databinding.ItemTextBinding;
import app.arbiterlab.ticandroid.library.libs.pair.TIC;
import app.arbiterlab.ticandroid.library.models.ChatModel;
import app.arbiterlab.ticandroid.ui.adapters.RequestParameterAdapter;

/**
 * Created by devkg on 2017-11-04.
 */

public class RequestAPIDialog extends Dialog {

    private DialogRequestApiBinding binding;
    private RequestParameterAdapter requestParameterAdapter;
    private Context context;
    private TIC tic;

    public RequestAPIDialog(Context context, TIC tic) {
        super(context);
        this.context = context;
        this.tic = tic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_request_api, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        requestParameterAdapter = new RequestParameterAdapter(context);

        ItemTextBinding footerTextBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_text, null, false);
        footerTextBinding.textView.setText("ADD NEW PARAMETER");

        binding.paramListView.setAdapter(requestParameterAdapter);
        binding.paramListView.addFooterView(footerTextBinding.getRoot());

        footerTextBinding.getRoot().setOnClickListener(view -> {
            requestParameterAdapter.addBlank();
            requestParameterAdapter.notifyDataSetChanged();
        });

        binding.sendButton.setOnClickListener(view -> {
            ChatModel chatModel = new ChatModel(tic);
            chatModel.sendMessage(requestParameterAdapter.getParameters());
            dismiss();
        });
    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }

}
