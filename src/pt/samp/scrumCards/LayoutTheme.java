package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayoutTheme {
    private static int backgroundColor = Theme.DEFAULT_BACKGROUND_COLOR;
    private static int cardColor = Theme.DEFAULT_CARD_COLOR;
    private static int textColor = Theme.DEFAULT_TEXT_COLOR;
    private static boolean fullCardWithBorder = Theme.DEFAULT_FULL_CARD_WITH_BORDER;

    private static boolean customTheme = false;
    private static boolean themeSaved = true;


    public static int getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(int backgroundColor) {
        customTheme = true;
        themeSaved = false;
        LayoutTheme.backgroundColor = backgroundColor;
    }

    public static int getCardColor() {
        return cardColor;
    }

    public static void setCardColor(int cardColor) {
        customTheme = true;
        themeSaved = false;
        LayoutTheme.cardColor = cardColor;
    }

    public static int getTextColor() {
        return textColor;
    }

    public static void setTextColor(int textColor) {
        customTheme = true;
        themeSaved = false;
        LayoutTheme.textColor = textColor;
    }

    public static boolean isFullCardWithBorder() {
        return fullCardWithBorder;
    }

    public static void setFullCardWithBorder(boolean fullCardWithBorder) {
        customTheme = true;
        themeSaved = false;
        LayoutTheme.fullCardWithBorder = fullCardWithBorder;
    }

    public static boolean isThemeSaved() {
        return themeSaved;
    }

    public static void setThemeSaved(boolean themeSaved) {
        LayoutTheme.themeSaved = themeSaved;
    }


    public static void updateBackground(Activity context, LinearLayout mainLayout, boolean force) {
        if (!customTheme && !force) {
            return;
        }
        mainLayout.setBackgroundColor(backgroundColor);
    }

    public static void updateCardSelector(Activity context, boolean force) {
        if (!customTheme && !force) {
            return;
        }
        updateBackground(context, (LinearLayout) context.findViewById(R.id.main_layout), force);

        PaintDrawable pd = new PaintDrawable();
        pd.getPaint().setColor(cardColor);
        pd.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources()
                .getDisplayMetrics()));

        TextView textView;
        for (int id : Cards.IDS) {
            textView = (TextView) context.findViewById(id);
            textView.setTextColor(textColor);
            textView.setBackgroundDrawable(pd);
        }
    }

    public static void updateFullCard(Context context, TextView textView) {
        if (!customTheme) {
            return;
        }

        textView.setTextColor(textColor);
        if (fullCardWithBorder) {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(cardColor);
            gd.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics()));
            gd.setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()), backgroundColor);
            textView.setBackgroundDrawable(gd);
        } else {
            textView.setBackgroundColor(cardColor);
        }
    }

    public static Theme getTheme() {
        return Theme.builder().backgroundColor(getBackgroundColor())
                              .cardColor(getCardColor())
                              .textColor(getTextColor())
                              .fullCardWithBorder(isFullCardWithBorder())
                              .build();
    }

    public static void update(Theme theme) {
        backgroundColor = theme.getBackgroundColor();
        cardColor = theme.getCardColor();
        textColor = theme.getTextColor();
        fullCardWithBorder = theme.isFullCardWithBorder();
        customTheme = true;
        themeSaved = true;
    }

    public static void reset() {
        backgroundColor = Theme.DEFAULT_BACKGROUND_COLOR;
        cardColor = Theme.DEFAULT_CARD_COLOR;
        textColor = Theme.DEFAULT_TEXT_COLOR;
        fullCardWithBorder = Theme.DEFAULT_FULL_CARD_WITH_BORDER;
        customTheme = false;
        themeSaved = true;
    }

}
