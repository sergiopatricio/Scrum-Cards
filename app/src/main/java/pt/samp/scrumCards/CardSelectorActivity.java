package pt.samp.scrumCards;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CardSelectorActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PREFERENCES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.loadPreferences(this);
        Preferences.setWindowFlags(this, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        for (int i = 0; i < Card.IDS.length; i++) {
            final int idCard = Card.IDS[i];
            final int position = i;

            findViewById(idCard).setOnClickListener(new View.OnClickListener() {
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
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_preferences) {
            startActivityForResult(new Intent(this, PreferencesActivity.class), REQUEST_CODE_PREFERENCES);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCard(int pos) {
        Intent intent = new Intent(this, CardShowActivity.class);
        intent.putExtra(CardShowActivity.CARD_TO_SHOW, pos);
        startActivity(intent);
    }
}
