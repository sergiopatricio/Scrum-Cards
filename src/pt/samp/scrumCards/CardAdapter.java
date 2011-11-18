package pt.samp.scrumCards;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.TextView;

public class CardAdapter extends BaseAdapter {
    Context context = null;

    public CardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Cards.VALUES.length;
    }

    @Override
    public Object getItem(int position) {
        return Cards.VALUES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return Cards.createCardView(context, Cards.VALUES[position]);
    }

}
