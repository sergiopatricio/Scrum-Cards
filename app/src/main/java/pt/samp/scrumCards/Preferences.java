package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

class Preferences {
    private static final String PREFERENCE_KEEP_SCREEN_ON = "keep_screen_on";
    private static final String PREFERENCE_TAP_TO_HIDE_SHOW = "tap_to_hide_show";
    private static final String PREFERENCE_KEEP_HIDDEN = "keep_hidden";
    private static final String PREFERENCE_CARD_COLOR = "card_color";

    private static boolean keepScreenOn = false;
    private static boolean tapToHideShow = false;
    private static boolean keepHidden = false;
    private static int cardColor = Color.BLACK;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldKeepScreenOn = keepScreenOn;
        boolean oldTapToHideShow = tapToHideShow;
        boolean oldKeepHidden = keepHidden;
        int oldCardColor = cardColor;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);
        tapToHideShow = sharedPreferences.getBoolean(PREFERENCE_TAP_TO_HIDE_SHOW, false);
        keepHidden = sharedPreferences.getBoolean(PREFERENCE_KEEP_HIDDEN, false);
        try {
            cardColor = Color.parseColor(sharedPreferences.getString(PREFERENCE_CARD_COLOR, cardColor + ""));
        } catch (Exception e){
            cardColor = Color.BLACK;
        }

        return  oldKeepScreenOn != keepScreenOn ||
                oldTapToHideShow != tapToHideShow ||
                oldCardColor != cardColor ||
                oldKeepHidden != keepHidden;
    }

    public static boolean tapToHideShow() {
        return tapToHideShow;
    }
    public static boolean keepHidden() {
        return keepHidden;
    }
    public static int cardColor() {
        return cardColor;
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
