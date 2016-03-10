package celebro.com.example.celebro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import celebro.com.example.celebro.R;

/**
 * Created by 502575517 on 3/3/2016.
 */
public class movieSpinnerAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> mItems;

    public movieSpinnerAdapter(Context mcontext, List<String> mValuelist) {
        this.mContext = mcontext;
        this.mItems = mValuelist;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null || !convertView .getTag().toString().equals("DROPDOWN")){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.toolbar_spinner_dropdown, parent, false);
            convertView.setTag("DROPDOWN");
        }
        TextView textview = (TextView) convertView.findViewById(R.id.spinner_item_text);
        textview.setText(getTitle(position));

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)

    {


        if (convertView == null || !convertView.getTag().toString().equals("NON_DROPDOWN")) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.toolbar_spinner_item_actionbar, parent, false);
            convertView.setTag("NON_DROPDOWN");
        }

        TextView textview = (TextView) convertView.findViewById(R.id.spinner_item_text);
        textview.setText(getTitle(position));

        return convertView;
    }

    public String getTitle(int position){
        return position >=0 && position < mItems.size() ? mItems.get(position) : "";
    }
}
