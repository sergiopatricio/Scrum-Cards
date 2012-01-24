package pt.samp.scrumCards;

import pt.samp.scrumCards.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThemeActivity extends Activity {

    private enum ColorOption {
        BACKGROUND, CARD, TEXT
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);


        Button button = (Button) findViewById(R.id.button_color_background);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateColorOption(ColorOption.BACKGROUND);
            }
        });

        button = (Button) findViewById(R.id.button_color_card);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateColorOption(ColorOption.CARD);
            }
        });

        button = (Button) findViewById(R.id.button_color_text);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateColorOption(ColorOption.TEXT);
            }
        });

        button = (Button) findViewById(R.id.button_theme_reset);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Theme.reset();
            }
        });

        button = (Button) findViewById(R.id.button_theme_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveTheme();
            }
        });

        button = (Button) findViewById(R.id.button_theme_load);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                loadTheme();
            }
        });
    }

    private void updateColorOption(ColorOption option) {
        OnColorChangedListener listener = null;
        int color;
        switch (option) {
        case BACKGROUND:
            color = Theme.getBackgroundColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    Theme.setBackgroundColor(color);
                }
            };
            break;
        case CARD:
            color = Theme.getCardColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    Theme.setCardColor(color);
                }
            };
            break;
        case TEXT:
            color = Theme.getTextColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    Theme.setTextColor(color);
                }
            };
            break;
        default:
            return;
        }
        (new ColorPickerDialog(this, listener, color)).show();
    }

    private void saveTheme() {

    }

    private void loadTheme() {

    }
}