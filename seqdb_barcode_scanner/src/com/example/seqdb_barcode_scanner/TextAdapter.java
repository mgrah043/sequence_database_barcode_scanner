package com.example.seqdb_barcode_scanner;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter{



    private Context context;
    private String[] texts = {"Lookup", "Get Contents", "Move", "Bulk Move"};

    public TextAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return 4;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLUE);
            tv.setTextSize(40);
        }
        else {
            tv = (TextView) convertView;
        }

            tv.setText(texts[position]);
        return tv;
    }
}