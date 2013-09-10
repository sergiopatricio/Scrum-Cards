package pt.samp.scrumCards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class CardShowActivity extends Activity {
    public static final String CARD_TO_SHOW = "pt.samp.scrumCards.CardPosToShow";

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.setWindowFlags(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        int cardPosition = getIntent().getIntExtra(CARD_TO_SHOW, 0);
        LinearLayout view = (LinearLayout) findViewById(R.id.show_container);
        View cardView = null;
        if (Preferences.scrollCards()) {
            MyGallery gallery = new MyGallery(this);
            gallery.setAdapter(new CardAdapter(this));
            gallery.setSelection(cardPosition);

            gallery.setBackgroundColor(Color.TRANSPARENT);
            gallery.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            gallery.setSpacing(0);
            gallery.setFadingEdgeLength(0);
            gallery.setUnselectedAlpha(1);
            gallery.setClickable(false);

            cardView = gallery;
        } else {
            cardView = Card.createCardView(this, view, cardPosition);
            gestureDetector = new GestureDetector(new MyGestureDetector());
        }
        view.addView(cardView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector != null) {
            return gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    private class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            CardShowActivity.this.finish();
            return true;
        }
    }

    private class MyGallery extends Gallery {
        public MyGallery(Context context) {
            super(context);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float difX = Math.abs(e2.getRawX() - e1.getRawX());
            float difY = Math.abs(e2.getRawY() - e1.getRawY());

            if (difY > difX) {
                CardShowActivity.this.finish();
                return true;
            } else {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        }
    }

}
