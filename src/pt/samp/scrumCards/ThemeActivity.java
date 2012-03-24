package pt.samp.scrumCards;

import java.util.List;

import pt.samp.scrumCards.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.app.AlertDialog;
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

    private enum ThemeOperation {

        RESET, SAVE, LOAD, DELETE
    }

    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);

        databaseAdapter = new DatabaseAdapter();

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
                processThemeOperation(ThemeOperation.RESET, null);
            }
        });

        button = (Button) findViewById(R.id.button_theme_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startSaveTheme();
            }
        });

        button = (Button) findViewById(R.id.button_theme_load);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                selectThemeFor(ThemeOperation.LOAD);
            }
        });

        button = (Button) findViewById(R.id.button_theme_delete);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                selectThemeFor(ThemeOperation.DELETE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAdapter.open(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseAdapter.close();
    }

    private void updateColorOption(ColorOption option) {
        OnColorChangedListener listener = null;
        int color;
        switch (option) {
        case BACKGROUND:
            color = LayoutTheme.getBackgroundColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    LayoutTheme.setBackgroundColor(color);
                }
            };
            break;
        case CARD:
            color = LayoutTheme.getCardColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    LayoutTheme.setCardColor(color);
                }
            };
            break;
        case TEXT:
            color = LayoutTheme.getTextColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    LayoutTheme.setTextColor(color);
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

    private void startSaveTheme() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.theme));
        builder.setMessage(getString(R.string.theme_insert_name));

        final EditText inputText = new EditText(this);
        inputText.setFilters(new InputFilter[] { getSimpleInputFilter() });
        builder.setView(inputText);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Theme theme = LayoutTheme.getTheme();
                theme.setName(inputText.getText().toString().trim());
                processThemeOperation(ThemeOperation.SAVE, theme);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // nothing to do
            }
        });
        builder.create().show();
    }


    private void selectThemeFor(final ThemeOperation operation) {
        List<Theme> themes = databaseAdapter.getAllThemesNames();
        final String[] names = new String[themes.size()];
        final long[] ids = new long[themes.size()];

        int i = 0;
        for (Theme theme : themes) {
            names[i] = theme.getName();
            ids[i] = theme.getId();
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (operation) {
        case LOAD:
            builder.setTitle(getString(R.string.theme_choose_to_load));
            break;
        case DELETE:
            builder.setTitle(getString(R.string.theme_choose_to_delete));
            break;
        default:
            break;
        }
        builder.setItems(names, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                processThemeOperation(operation, databaseAdapter.getTheme(ids[item]));
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // nothing to do
            }
        });
        builder.create().show();
    }

    private void processThemeOperation(ThemeOperation operation, Theme theme) {
        //TODO: notifications and error treatment
        switch (operation) {
        case RESET:
            LayoutTheme.reset();
            Preferences.setIdTheme(this, Theme.DEFAULT_THEME_ID);
            break;
        case SAVE:
            databaseAdapter.insertTheme(theme);
            break;
        case LOAD:
            LayoutTheme.update(theme);
            Preferences.setIdTheme(this, theme.getId());
            break;
        case DELETE:
            databaseAdapter.deleteTheme(theme.getId());
            break;
        default:
            break;
        }
    }

}