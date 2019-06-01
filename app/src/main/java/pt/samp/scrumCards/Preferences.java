package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.WindowManager;

class Preferences {
    private static final String PREFERENCE_KEEP_SCREEN_ON = "keep_screen_on";
    private static final String PREFERENCE_TAP_TO_HIDE_SHOW = "tap_to_hide_show";

    private static boolean keepScreenOn = false;
    private static boolean tapToHideShow = false;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldKeepScreenOn = keepScreenOn;
        boolean oldTapToHideShow = tapToHideShow;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);
        tapToHideShow = sharedPreferences.getBoolean(PREFERENCE_TAP_TO_HIDE_SHOW, false);

        return oldKeepScreenOn != keepScreenOn || oldTapToHideShow != tapToHideShow;
    }

    public static boolean tapToHideShow() {
        return tapToHideShow;
    }

    public static void setWindowFlags(Activity activity, boolean fullScreen) {
        if (fullScreen) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (keepScreenOn) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
