package pt.samp.scrumCards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public final class Cards {
    public static final int[] IDS = new int[] { R.id.card0, R.id.card0_5, R.id.card1, R.id.card2, R.id.card3,
            R.id.card5, R.id.card8, R.id.card13, R.id.card20, R.id.card40, R.id.card100, R.id.cardq };

    public static final String[] VALUES = new String[] { "0", "1/2", "1", "2", "3", "5", "8", "13", "20", "40", "100",
            "?" };

    public static TextView createCardView(Context context, ViewGroup parent, String cardValue) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.card, parent, false);
        textView.setText(cardValue);
        LayoutTheme.updateFullCard(context, textView);
        return textView;
    }
}
