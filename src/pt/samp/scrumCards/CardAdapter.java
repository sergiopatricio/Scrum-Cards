package pt.samp.scrumCards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
