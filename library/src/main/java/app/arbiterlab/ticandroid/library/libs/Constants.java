package app.arbiterlab.ticandroid.library.libs;

import java.util.UUID;

/**
 * Created by Gyeongrok Kim on 2017-09-25.
 */

public class Constants {
    public static final UUID UUID_OTHER_DEVICE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final String MESSAGE_STATE_CHANGED = "s.tat-e/ch@nged";
    public static final String MESSAGE_ON_TEXT = "o.n.tex*t";

    public static final int RESULT_STATECHANGED = 2001;
    public static final int RESULT_MESSAGE = 2002;
}
