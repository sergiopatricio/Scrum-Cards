package pt.samp.scrumCards;

import android.app.Activity;
import android.os.Bundle;

public class InformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
    }
}