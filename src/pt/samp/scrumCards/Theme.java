package pt.samp.scrumCards;

import android.graphics.Color;

public class Theme {
    public static final int DEFAULT_THEME_ID = 0;
    public static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    public static final int DEFAULT_CARD_COLOR = Color.WHITE;
    public static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    public static final boolean DEFAULT_FULL_CARD_WITH_BORDER = true;

    private long id = DEFAULT_THEME_ID;
    private String name = null;
    private int backgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int cardColor = DEFAULT_CARD_COLOR;
    private int textColor = DEFAULT_TEXT_COLOR;
    private boolean fullCardWithBorder = DEFAULT_FULL_CARD_WITH_BORDER;

    public static class Builder {
        private final Theme theme;

        private Builder() { theme = new Theme(); }

        public Builder id(long id) { theme.id = id; return this; }
        public Builder name(String name) { theme.name = name; return this; }
        public Builder backgroundColor(int backgroundColor) { theme.backgroundColor = backgroundColor; return this; }
        public Builder cardColor(int cardColor) { theme.cardColor = cardColor; return this; }
        public Builder textColor(int textColor) { theme.textColor = textColor; return this; }
        public Builder fullCardWithBorder(boolean fullCardWithBorder) { theme.fullCardWithBorder = fullCardWithBorder; return this; }

        public Theme build() {
            return this.theme;
        }

    }

    private Theme() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getCardColor() {
        return cardColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public boolean isFullCardWithBorder() {
        return fullCardWithBorder;
    }

}
