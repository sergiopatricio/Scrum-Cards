package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class Preferences {
    private static final String PREFERENCE_SCROLL_CARDS = "scroll_cards";
    private static final String PREFERENCE_KEEP_SCREEN_ON = "keep_screen_on";

    private static boolean scrollCards = false;
    private static boolean keepScreenOn = false;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldScrollCards = scrollCards;
        boolean oldKeepScreenOn = keepScreenOn;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        scrollCards = sharedPreferences.getBoolean(PREFERENCE_SCROLL_CARDS, false);
        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);

        if (oldScrollCards != scrollCards || oldKeepScreenOn != keepScreenOn) {
            return true;
        }
        return false;
    }

    public static boolean scrollCards() {
        return scrollCards;
    }

    public static boolean keepScreenOn() {
        return keepScreenOn;
    }

    public static void setWindowFlags(Activity activity, boolean fullScreen) {
        if (fullScreen) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (keepScreenOn) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
