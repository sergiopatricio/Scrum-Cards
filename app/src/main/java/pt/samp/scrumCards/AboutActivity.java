package pt.samp.scrumCards;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }
}
