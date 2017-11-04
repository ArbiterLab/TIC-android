package app.arbiterlab.ticandroid.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import app.arbiterlab.ticandroid.R;
import app.arbiterlab.ticandroid.databinding.ItemParamBinding;
import app.arbiterlab.ticandroid.library.datas.TicParam;

/**
 * Created by devkg on 2017-11-04.
 */

public class RequestParameterAdapter extends BaseAdapter {

    private Context context;
    private HashMap<Integer, TicParam> ticParams = new HashMap<>();

    public RequestParameterAdapter(final Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ticParams.size();
    }

    @Override
    public TicParam getItem(int i) {
        return ticParams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int p, View view, ViewGroup viewGroup) {
        final ItemParamBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_param, null, false);
        final TicParam ticParam = ticParams.get(p);

        binding.paramKey.setText(ticParam.getKey());
        binding.paramKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ticParam.setKey(String.valueOf(charSequence));
                ticParams.put(p, ticParam);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.paramValue.setText(ticParam.getValue());
        binding.paramValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ticParam.setValue(String.valueOf(charSequence));
                ticParams.put(p, ticParam);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return binding.getRoot();
    }

    public void addBlank() {
        TicParam ticParam = new TicParam("", "");
        ticParams.put(ticParams.size(), ticParam);
    }

    public TicParam[] getParameters(){
        return ticParams.values().toArray(new TicParam[0]);
    }
}
