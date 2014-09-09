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
    private static final String PREFERENCE_TAP_TO_HIDE_SHOW = "tap_to_hide_show";

    private static boolean scrollCards = false;
    private static boolean keepScreenOn = false;
    private static boolean tapToHideShow = false;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldScrollCards = scrollCards;
        boolean oldKeepScreenOn = keepScreenOn;
        boolean oldTapToHideShow = tapToHideShow;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        scrollCards = sharedPreferences.getBoolean(PREFERENCE_SCROLL_CARDS, false);
        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);
        tapToHideShow = sharedPreferences.getBoolean(PREFERENCE_TAP_TO_HIDE_SHOW, false);

        if (oldScrollCards != scrollCards || oldKeepScreenOn != keepScreenOn || oldTapToHideShow != tapToHideShow) {
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

    public static boolean tapToHideShow() {
        return tapToHideShow;
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
