package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class Preferences {
    private static final String PREFERENCE_SCROLL_CARDS = "scroll_cards";
    private static final String PREFERENCE_FULLSCREEN = "fullscreen";
    private static final String PREFERENCE_KEEP_SCREEN_ON = "keep_screen_on";

    private static boolean scrollCards = false;
    private static boolean showInFullscreen = false;
    private static boolean keepScreenOn = false;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldScrollCards = scrollCards;
        boolean oldShowInFullscreen = showInFullscreen;
        boolean oldKeepScreenOn = keepScreenOn;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        scrollCards = sharedPreferences.getBoolean(PREFERENCE_SCROLL_CARDS, false);
        showInFullscreen = sharedPreferences.getBoolean(PREFERENCE_FULLSCREEN, false);
        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);

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

    public static void setWindowFlags(Activity activity, boolean forceFullScreen) {
        if (showInFullscreen || forceFullScreen) {
            /*
             *  this is problematic if the device has no menu button, with the title disabled the menu is not accessible
             *  possible solutions:
             *    only use fullscreen on card show
             *    add some shortcut/feature to exit fullscreen or enter preferences
             */
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (keepScreenOn) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}