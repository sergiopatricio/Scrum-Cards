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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        CheckBox checkBox = (CheckBox) findViewById(R.id.check_full_card_with_border);
        checkBox.setChecked(LayoutTheme.isFullCardWithBorder());
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateFullCardWithBorder(((CheckBox) view).isChecked());
            }
        });


        button = (Button) findViewById(R.id.button_theme_default);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                processThemeOperation(ThemeOperation.RESET, Theme.DEFAULT_THEME_ID, null);
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

        String themeName = getString(R.string.theme_default);
        if (LayoutTheme.isThemeSaved()) {
            long idTheme = Preferences.getIdTheme();
            if (idTheme != Theme.DEFAULT_THEME_ID) {
                Theme theme = databaseAdapter.getTheme(idTheme);
                if (theme != null) {
                    themeName = theme.getName();
                }
            }
        } else {
            themeName = getString(R.string.theme_customized_not_saved);
        }

        showActiveTheme(themeName);
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
                    showActiveTheme(getString(R.string.theme_customized_not_saved));
                }
            };
            break;
        case CARD:
            color = LayoutTheme.getCardColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    LayoutTheme.setCardColor(color);
                    showActiveTheme(getString(R.string.theme_customized_not_saved));
                }
            };
            break;
        case TEXT:
            color = LayoutTheme.getTextColor();
            listener = new OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    LayoutTheme.setTextColor(color);
                    showActiveTheme(getString(R.string.theme_customized_not_saved));
                }
            };
            break;
        default:
            return;
        }
        (new ColorPickerDialog(this, listener, color)).show();
    }

    private void updateFullCardWithBorder(boolean fullCardWihBorder) {
        LayoutTheme.setFullCardWithBorder(fullCardWihBorder);
        showActiveTheme(getString(R.string.theme_customized_not_saved));
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
                processThemeOperation(ThemeOperation.SAVE, Theme.DEFAULT_THEME_ID, theme);
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
                processThemeOperation(operation, ids[item], null);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // nothing to do
            }
        });
        builder.create().show();
    }

    private void processThemeOperation(ThemeOperation operation, long idTheme, Theme theme) {
        switch (operation) {
        case RESET:
            LayoutTheme.reset();
            Preferences.setIdTheme(this, Theme.DEFAULT_THEME_ID);
            showMessage(R.string.theme_info_update_to_default);
            showActiveTheme(getString(R.string.theme_default));
            break;
        case SAVE:
            long id = databaseAdapter.insertTheme(theme);
            if (id > 0) {
                LayoutTheme.update(theme);
                Preferences.setIdTheme(this, id);
                showMessage(R.string.theme_info_saved);
                showActiveTheme(theme.getName());
            } else {
                showMessage(R.string.theme_info_not_saved);
            }
            break;
        case LOAD:
            Theme newTheme = databaseAdapter.getTheme(idTheme);
            LayoutTheme.update(newTheme);
            Preferences.setIdTheme(this, newTheme.getId());
            showMessage(R.string.theme_info_loaded);
            showActiveTheme(newTheme.getName());
            break;
        case DELETE:
            if (databaseAdapter.deleteTheme(idTheme)) {
                if (idTheme == Preferences.getIdTheme()) {
                    Preferences.setIdTheme(this, Theme.DEFAULT_THEME_ID);
                    LayoutTheme.setThemeSaved(false);
                    showActiveTheme(getString(R.string.theme_customized_not_saved));
                }
                showMessage(R.string.theme_info_deleted);
            } else {
                showMessage(R.string.theme_info_not_deleted);
            }
            break;
        default:
            break;
        }
    }

    private void showMessage(int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    private void showActiveTheme(String name) {
        TextView textView = (TextView) findViewById(R.id.current_theme_name);
        textView.setText(getString(R.string.theme_current, name));
    }

}
