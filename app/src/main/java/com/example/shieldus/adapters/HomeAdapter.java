package com.example.shieldus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shieldus.models.HomeItem;
import com.example.shieldus.R;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HomeItem> homeItems;

    public HomeAdapter(Context context, ArrayList<HomeItem> homeItems) {
        this.context = context;
        this.homeItems = homeItems;
    }

    @Override
    public int getCount() {
        return homeItems.size();
    }

    @Override
    public Object getItem(int position) {
        return homeItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        }

        HomeItem currentItem = homeItems.get(position);

        ImageView icon = convertView.findViewById(R.id.itemIcon);
        TextView title = convertView.findViewById(R.id.itemTitle);

        icon.setImageResource(currentItem.getIconResId());
        title.setText(currentItem.getTitle());

        return convertView;
    }
}