package pt.samp.scrumCards;

import pt.samp.scrumCards.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        button = (Button) findViewById(R.id.button_theme_delete);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteTheme();
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

    private InputFilter getSimpleInputFilter() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isSpace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
    }

    private void saveTheme() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.theme));
        builder.setMessage(getString(R.string.theme_insert_name));


        final EditText inputText = new EditText(this);
        inputText.setFilters(new InputFilter[] { getSimpleInputFilter() });
        builder.setView(inputText);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //inputText.getText().toString().trim()
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // nothing to do
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void loadTheme() {
        //TODO: load this from DB (and include default theme option)
        String[] names = new String[] { getString(R.string.theme_default) };
        int[] ids = new int[] { 0 };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.theme_choose_to_load));
        builder.setItems(names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
              //ids[item]
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // nothing to do
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void deleteTheme() {

    }


}