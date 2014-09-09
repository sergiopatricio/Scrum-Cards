package pt.samp.scrumCards;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this, false);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}