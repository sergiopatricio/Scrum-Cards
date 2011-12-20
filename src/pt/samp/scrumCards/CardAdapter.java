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
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        //return inflater.inflate(R.layout.card, parent, false);

        return Cards.createCardView(context, parent, Cards.VALUES[position]);
    }

}
