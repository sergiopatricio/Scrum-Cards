package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
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

    private static final String[] SHIRT_SIZES = new String[] {
        "0", // 0
        "XXS", // 1/2
        "XS", // 1
        "S", // 2
        "M", // 3
        "L", // 5
        "XL", // 8
        "XXL", // 13
        "3XL", // 20
        "4XL", // 40
        "5XL" // 100
    };

    private static Rect textBounds = new Rect();

    private static Bitmap textAsBitmap(Context context, String text, float textSize, int textColor) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.getTextBounds(text, 0, text.length(), textBounds);

//        float desiredTextSize = textSize * width / textBounds.width();
//        paint.setTextSize(desiredTextSize);

        paint.getTextBounds(text, 0, text.length(), textBounds);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, (width/2) - textBounds.exactCenterX(), (height/2) - textBounds.exactCenterY(), paint);

        return image;
    }

    public static ImageView createCardView(Context context, ViewGroup parent, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) inflater.inflate(R.layout.card, parent, false);

        if (Preferences.useShirtSizes()) {
            if (position >= SHIRT_SIZES.length || position == 0) {
                imageView.setImageResource(Card.IMAGES[position]);
            } else {
                imageView.setImageBitmap(Card.textAsBitmap(context, Card.SHIRT_SIZES[position], 350, Color.BLACK));
            }
        } else {
            imageView.setImageResource(Card.IMAGES[position]);
        }

//        Resources resources = context.getResources();
//        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, Card.IMAGES[position]));

        return imageView;
    }
}
