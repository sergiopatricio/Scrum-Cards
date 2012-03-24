package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Theme {
    private static int DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    private static int DEFAULT_CARD_COLOR = Color.WHITE;
    private static int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static boolean DEFAULT_FULL_CARD_WITH_BORDER = true;

    private static int backgroundColor = DEFAULT_BACKGROUND_COLOR;
    private static int cardColor = DEFAULT_CARD_COLOR;
    private static int textColor = DEFAULT_TEXT_COLOR;
    private static boolean fullCardWithBorder = DEFAULT_FULL_CARD_WITH_BORDER;

    private static boolean defaultThemeActive = false;
    private static boolean themeChanged = false;


    public static int getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(int backgroundColor) {
        themeChanged = true;
        Theme.backgroundColor = backgroundColor;
    }

    public static int getCardColor() {
        return cardColor;
    }

    public static void setCardColor(int cardColor) {
        themeChanged = true;
        Theme.cardColor = cardColor;
    }

    public static int getTextColor() {
        return textColor;
    }

    public static void setTextColor(int textColor) {
        themeChanged = true;
        Theme.textColor = textColor;
    }

    public static boolean isFullCardWithBorder() {
        return fullCardWithBorder;
    }

    public static void setFullCardWithBorder(boolean fullCardWithBorder) {
        themeChanged = true;
        Theme.fullCardWithBorder = fullCardWithBorder;
    }

    public static boolean isDefaultThemeActive() {
        return defaultThemeActive;
    }

    public static void setDefaultThemeActive(boolean defaultThemeActive) {
        Theme.defaultThemeActive = defaultThemeActive;
    }

    public static boolean isThemeChanged() {
        return themeChanged;
    }



    public static void updateBackground(Activity context, LinearLayout mainLayout) {
        if (defaultThemeActive) {
            return;
        }
        mainLayout.setBackgroundColor(backgroundColor);
    }

    public static void updateCardSelector(Activity context) {
        if (defaultThemeActive) {
            return;
        }
        updateBackground(context, (LinearLayout) context.findViewById(R.id.main_layout));

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
        if (defaultThemeActive) {
            return;
        }
        //TODO: option for full card without border
        textView.setTextColor(textColor);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(cardColor);
        gd.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics()));
        gd.setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()), backgroundColor);
        textView.setBackgroundDrawable(gd);
    }

    public static void reset() {
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
        cardColor = DEFAULT_CARD_COLOR;
        textColor = DEFAULT_TEXT_COLOR;
        fullCardWithBorder = DEFAULT_FULL_CARD_WITH_BORDER;
        themeChanged = false;
    }

}
