package ca.gc.aafc.seqdb_barcode_scanner;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Used to display menu options in the grid version of main menu
 * 
 * NOT CURRENTLY USED
 */
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
            tv.setBackgroundColor(Color.parseColor("#33b5e5"));
            tv.setTextSize(40);
            tv.setLines(5); //TODO properly set cell height
            tv.setGravity(Gravity.CENTER);
        }
        else {
            tv = (TextView) convertView;
        }

            tv.setText(texts[position]);
        return tv;
    }
}