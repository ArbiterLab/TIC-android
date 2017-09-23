package app.arbiterlab.ticandroid.library;

import android.content.Intent;

/**
 * Created by Gyeongrok Kim on 2017-09-23.
 */

public interface ActivityResultCallback {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
