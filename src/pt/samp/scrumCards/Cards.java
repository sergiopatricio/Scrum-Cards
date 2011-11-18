package pt.samp.scrumCards;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Gallery.LayoutParams;
import android.widget.TextView;


public final class Cards {
    public static final int[] IDS = new int[] { R.id.card0, R.id.card0_5, R.id.card1, R.id.card2, R.id.card3,
            R.id.card5, R.id.card8, R.id.card13, R.id.card20, R.id.card40, R.id.card100, R.id.cardq };

    public static final String[] VALUES = new String[] { "0", "1/2", "1", "2", "3", "5", "8", "13", "20", "40", "100",
            "?" };

    public static TextView createCardView(Context context, String cardValue) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        textView.setBackgroundResource(R.drawable.backgound_card_show);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(180);
        textView.setTextColor(Color.BLACK);
        textView.setText(cardValue);
        return textView;
    }
}
