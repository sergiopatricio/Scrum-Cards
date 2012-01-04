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
        String theme = getSharedPreferences(context).getString("theme", "black_on_white_1");
        int idTheme = R.style.Theme_black_on_white_1;
        if ("black_on_white_1".equals(theme)) {
            idTheme = R.style.Theme_black_on_white_1;
        } else if ("black_on_white_2".equals(theme)) {
            idTheme = R.style.Theme_black_on_white_2;
        } else if ("white_on_black_1".equals(theme)) {
            idTheme = R.style.Theme_white_on_black_1;
        } else if ("white_on_black_2".equals(theme)) {
            idTheme = R.style.Theme_white_on_black_2;
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