package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CardSelectorActivity extends Activity {
    private static int PREFERENCES_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Preferences.loadPreferences(this);
        Preferences.setWindowFlags(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView textView;
        for (int id : Cards.IDS) {
            textView = (TextView) findViewById(id);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    showCard(Integer.parseInt((String) view.getTag()));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for preferences change
        if (requestCode == PREFERENCES_REQUEST_CODE && Preferences.loadPreferences(this)) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_information:
            startActivity(new Intent(this, InformationActivity.class));
            return true;
        case R.id.menu_preferences:
            startActivityForResult(new Intent(this, PreferencesActivity.class), PREFERENCES_REQUEST_CODE);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void showCard(int pos) {
        Intent intent = new Intent(this, CardShowActivity.class);
        intent.putExtra(CardShowActivity.CARD_TO_SHOW, pos);
        startActivity(intent);
    }

}
