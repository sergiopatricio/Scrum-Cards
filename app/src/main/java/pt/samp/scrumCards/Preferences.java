package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

class Preferences {
    private static final String PREFERENCE_KEEP_SCREEN_ON = "keep_screen_on";
    private static final String PREFERENCE_TAP_TO_HIDE_SHOW = "tap_to_hide_show";
    private static final String PREFERENCE_USE_SHIRT_SIZES = "use_shirt_sizes";

    private static boolean keepScreenOn = false;
    private static boolean tapToHideShow = false;
    private static boolean useShirtSizes = false;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldKeepScreenOn = keepScreenOn;
        boolean oldTapToHideShow = tapToHideShow;
        boolean oldUseShirtSizes = useShirtSizes;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);
        tapToHideShow = sharedPreferences.getBoolean(PREFERENCE_TAP_TO_HIDE_SHOW, false);
        useShirtSizes = sharedPreferences.getBoolean(PREFERENCE_USE_SHIRT_SIZES, false);

        return oldKeepScreenOn != keepScreenOn || oldTapToHideShow != tapToHideShow || oldUseShirtSizes != useShirtSizes;
    }

    public static boolean tapToHideShow() {
        return tapToHideShow;
    }

    public static boolean useShirtSizes() {
        return useShirtSizes;
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
