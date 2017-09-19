package app.arbiterlab.ticandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.arbiterlab.ticandroid.library.TIC;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TIC.init(this);
    }
}
