package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class Preferences {
    private static boolean scrollCards = false;
    private static boolean showInFullscreen = false;
    private static boolean keepScreenOn = false;

    /**
     *
     * @param context
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldScrollCards = scrollCards;
        boolean oldShowInFullscreen = showInFullscreen;
        boolean oldKeepScreenOn = keepScreenOn;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        scrollCards = sharedPreferences.getBoolean("scroll_cards", false);
        showInFullscreen = sharedPreferences.getBoolean("fullscreen", false);
        keepScreenOn = sharedPreferences.getBoolean("keep_screen_on", false);

        if (oldScrollCards != scrollCards || oldShowInFullscreen != showInFullscreen || oldKeepScreenOn != keepScreenOn) {
            return true;
        }
        return false;
    }

    public static boolean scrollCards() {
        return scrollCards;
    }

    public static boolean showInFullscreen() {
        return showInFullscreen;
    }

    public static boolean keepScreenOn() {
        return keepScreenOn;
    }

    public static void setWindowFlags(Activity activity) {
        if (showInFullscreen) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (keepScreenOn) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}