package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public final class Cards {
    public static final int[] IDS = new int[] { R.id.card0, R.id.card0_5, R.id.card1, R.id.card2, R.id.card3,
            R.id.card5, R.id.card8, R.id.card13, R.id.card20, R.id.card40, R.id.card100, R.id.cardq };

    public static final String[] VALUES = new String[] { "0", "1/2", "1", "2", "3", "5", "8", "13", "20", "40", "100",
            "?" };

    // values based on tests with multiple screen resolutions
    private static final float[] VERTICAL_FACTOR = new float[] { 2.5f, 4f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f, 3f, 3f, 3f,
            4f, 2.5f };
    private static final float HORIZONTAL_FACTOR = 2.5f;

    public static TextView createCardView(Context context, ViewGroup parent, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.card, parent, false);
        textView.setText(VALUES[position]);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        // set font size based on the screen size
        int newSize;
        if (displaymetrics.heightPixels > displaymetrics.widthPixels) {
            newSize = (int) (displaymetrics.heightPixels / VERTICAL_FACTOR[position]);
        } else {
            newSize = (int) (displaymetrics.widthPixels / HORIZONTAL_FACTOR);
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);

        return textView;
    }
}
