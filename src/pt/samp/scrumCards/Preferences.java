package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class Preferences extends PreferenceActivity {
    private static final String THEME_DEFAULT = "default";
    private static final String THEME_WHITE_ON_BLACK = "white_on_black";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean scrollCards(Context context) {
        return getSharedPreferences(context).getBoolean("scroll_cards", false);
    }

    public static boolean showInFullscreen(Context context) {
        return getSharedPreferences(context).getBoolean("fullscreen", false);
    }

    public static boolean keepScreenOn(Context context) {
        return getSharedPreferences(context).getBoolean("keep_screen_on", false);
    }

    public static int getTheme(Context context) {
        String theme = getSharedPreferences(context).getString("theme", THEME_DEFAULT);
        int idTheme = R.style.Theme_default;
        if (theme.equals(THEME_DEFAULT)) {
            idTheme = R.style.Theme_default;
        } else if (theme.equals(THEME_WHITE_ON_BLACK)) {
            idTheme = R.style.Theme_1;
        }

        return idTheme;
    }

    public static void setWindowFlags(Activity activity) {
        if (showInFullscreen(activity)) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (keepScreenOn(activity)) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}