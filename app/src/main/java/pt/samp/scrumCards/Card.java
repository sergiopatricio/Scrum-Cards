package pt.samp.scrumCards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

final class Card {
    public static final int[] IDS = new int[] {
        R.id.card0,
        R.id.card1_2,
        R.id.card1,
        R.id.card2,
        R.id.card3,
        R.id.card5,
        R.id.card8,
        R.id.card13,
        R.id.card20,
        R.id.card40,
        R.id.card100,
        R.id.cardquestion,
        R.id.cardinfinite,
        R.id.cardcoffee
    };

    private static final int[] IMAGES = new int[] {
        R.drawable.card_big_0,
        R.drawable.card_big_1_2,
        R.drawable.card_big_1,
        R.drawable.card_big_2,
        R.drawable.card_big_3,
        R.drawable.card_big_5,
        R.drawable.card_big_8,
        R.drawable.card_big_13,
        R.drawable.card_big_20,
        R.drawable.card_big_40,
        R.drawable.card_big_100,
        R.drawable.card_big_question,
        R.drawable.card_big_infinite,
        R.drawable.card_big_coffee
    };

    public static ImageView createCardView(Context context, ViewGroup parent, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) inflater.inflate(R.layout.card, parent, false);
        imageView.setImageResource(Card.IMAGES[position]);

//        Resources resources = context.getResources();
//        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, Card.IMAGES[position]));

        return imageView;
    }
}
