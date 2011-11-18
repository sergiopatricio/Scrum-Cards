package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class CardSelector extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preferences.keepScreenOn(this)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
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
            startActivity(new Intent(this, Information.class));
            return true;
        case R.id.menu_preferences:
            startActivity(new Intent(this, Preferences.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void showCard(int pos) {
        Intent intent = new Intent(this, CardShow.class);
        intent.putExtra(CardShow.CARD_TO_SHOW, pos);
        startActivity(intent);
    }

}
