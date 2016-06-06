package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class CardSelectorActivity extends Activity {
    private static final int REQUEST_CODE_PREFERENCES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.loadPreferences(this);
        Preferences.setWindowFlags(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        for (int i = 0; i < Card.IDS.length; i++) {
            final int position = i;
            ImageView view = (ImageView)findViewById(Card.IDS[i]);
            view.setColorFilter(Preferences.cardColor(), PorterDuff.Mode.SRC_ATOP);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    showCard(position);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PREFERENCES && Preferences.loadPreferences(this)) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_about:
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        case R.id.menu_preferences:
            startActivityForResult(new Intent(this, PreferencesActivity.class), REQUEST_CODE_PREFERENCES);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void showCard(int pos) {
        Globals g = (Globals)getApplication();
        g.isCardVisible = true;
        Intent intent = new Intent(this, CardShowActivity.class);
        intent.putExtra(CardShowActivity.CARD_TO_SHOW, pos);
        startActivity(intent);
    }
}
