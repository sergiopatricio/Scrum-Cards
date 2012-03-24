package pt.samp.scrumCards;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
    private static final String DATABASE_NAME = "scrumcards.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_THEMES = "themes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BACKGROUND_COLOR = "background_color";
    private static final String COLUMN_CARD_COLOR = "card_color";
    private static final String COLUMN_TEXT_COLOR = "text_color";
    private static final String COLUMN_FULL_CARD_WITH_BORDER = "full_card_with_border";

    private static final String CREATE_DATABASE = "create table " + TABLE_THEMES + "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_BACKGROUND_COLOR + " integer not null, " +
            COLUMN_CARD_COLOR + " integer not null, " +
            COLUMN_TEXT_COLOR + " integer not null, " +
            COLUMN_FULL_CARD_WITH_BORDER + " integer not null);";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //nothing to do, database will be create for the first time
        }
    }

    public DatabaseAdapter() {
    }


    public void open(Context context) throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public long insertTheme(Theme theme) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, theme.getName());
        values.put(COLUMN_BACKGROUND_COLOR, Integer.valueOf(theme.getBackgroundColor()));
        values.put(COLUMN_CARD_COLOR, Integer.valueOf(theme.getCardColor()));
        values.put(COLUMN_TEXT_COLOR, Integer.valueOf(theme.getTextColor()));
        values.put(COLUMN_FULL_CARD_WITH_BORDER, Boolean.valueOf(theme.isFullCardWithBorder()));
        return database.insert(TABLE_THEMES, null, values);
    }

    public boolean deleteTheme(long idTheme) {
        return database.delete(TABLE_THEMES, COLUMN_ID + "=" + idTheme, null) > 0;
    }

    public List<Theme> getAllThemes() {
        List<Theme> themes = new ArrayList<Theme>();
        Cursor cursor = database.query(TABLE_THEMES, new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_BACKGROUND_COLOR,
                COLUMN_CARD_COLOR, COLUMN_TEXT_COLOR, COLUMN_FULL_CARD_WITH_BORDER }, null, null, null, null,
                COLUMN_NAME);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int i = 0;
            themes.add(Theme.builder().id(cursor.getLong(i++))
                                      .name(cursor.getString(i++))
                                      .backgroundColor(cursor.getInt(i++))
                                      .cardColor(cursor.getInt(i++))
                                      .textColor(cursor.getInt(i++))
                                      .fullCardWithBorder(cursor.getInt(i++) == 1)
                                      .build());
            cursor.moveToNext();
        }
        cursor.close();
        return themes;
    }

    public List<Theme> getAllThemesNames() {
        List<Theme> themes = new ArrayList<Theme>();
        Cursor cursor = database.query(TABLE_THEMES, new String[] { COLUMN_ID, COLUMN_NAME }, null, null, null, null,
                COLUMN_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int i = 0;
            themes.add(Theme.builder().id(cursor.getLong(i++)).name(cursor.getString(i++)).build());
            cursor.moveToNext();
        }
        cursor.close();
        return themes;
    }

    public Theme getTheme(long idTheme) throws SQLException {
        Cursor cursor = database.query(true, TABLE_THEMES, new String[] { COLUMN_ID, COLUMN_NAME,
                COLUMN_BACKGROUND_COLOR, COLUMN_CARD_COLOR, COLUMN_TEXT_COLOR, COLUMN_FULL_CARD_WITH_BORDER },
                COLUMN_ID + "=" + idTheme, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int i = 0;
            return Theme.builder().id(cursor.getLong(i++))
                                  .name(cursor.getString(i++))
                                  .backgroundColor(cursor.getInt(i++))
                                  .cardColor(cursor.getInt(i++))
                                  .textColor(cursor.getInt(i++))
                                  .fullCardWithBorder(cursor.getInt(i++) == 1)
                                  .build();
        }
        return null;
    }

    public boolean updateTheme(Theme theme) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, theme.getName());
        values.put(COLUMN_BACKGROUND_COLOR, Integer.valueOf(theme.getBackgroundColor()));
        values.put(COLUMN_CARD_COLOR, Integer.valueOf(theme.getCardColor()));
        values.put(COLUMN_TEXT_COLOR, Integer.valueOf(theme.getTextColor()));
        values.put(COLUMN_FULL_CARD_WITH_BORDER, Boolean.valueOf(theme.isFullCardWithBorder()));
        return database.update(TABLE_THEMES, values, COLUMN_ID + "=" + theme.getId(), null) > 0;
    }
}
