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
    private static final String PREFERENCE_ID_THEME = "id_theme";

    private static boolean scrollCards = false;
    private static boolean showInFullscreen = false;
    private static boolean keepScreenOn = false;
    private static long idTheme = Theme.DEFAULT_THEME_ID;

    /**
     * @return true if some preference changed, false if nothing changed
     */
    public static boolean loadPreferences(Context context) {
        boolean oldScrollCards = scrollCards;
        boolean oldShowInFullscreen = showInFullscreen;
        boolean oldKeepScreenOn = keepScreenOn;
        long oldIdTheme = idTheme;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        scrollCards = sharedPreferences.getBoolean(PREFERENCE_SCROLL_CARDS, false);
        showInFullscreen = sharedPreferences.getBoolean(PREFERENCE_FULLSCREEN, false);
        keepScreenOn = sharedPreferences.getBoolean(PREFERENCE_KEEP_SCREEN_ON, false);
        idTheme = sharedPreferences.getLong(PREFERENCE_ID_THEME, Theme.DEFAULT_THEME_ID);

        if (oldScrollCards != scrollCards || oldShowInFullscreen != showInFullscreen || oldKeepScreenOn != keepScreenOn
                || oldIdTheme != idTheme) {
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

    public static long getIdTheme() {
        return idTheme;
    }

    public static void setIdTheme(Context context, long idTheme) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(PREFERENCE_ID_THEME, idTheme);
        editor.commit();
        Preferences.idTheme = idTheme;
    }

    public static void setWindowFlags(Activity activity) {
        if (showInFullscreen) {
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