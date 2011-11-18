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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class CardShow extends Activity {
    public static final String CARD_TO_SHOW = "pt.samp.scrumCards.CardPosToShow";

    private GestureDetector gestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preferences.showCardInFullscreen(this)) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Preferences.keepScreenOn(this)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.show);

        int cardPosition = getIntent().getIntExtra(CARD_TO_SHOW, 0);

        View cardView = null;
        if (Preferences.scrollCards(this)) {
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
            cardView = Cards.createCardView(this, Cards.VALUES[cardPosition]);
            gestureDetector = new GestureDetector(new MyGestureDetector());
        }

        LinearLayout view = (LinearLayout) findViewById(R.id.card_show);
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
            CardShow.this.finish();
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
                CardShow.this.finish();
                return true;
            } else {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        }
    }

}
