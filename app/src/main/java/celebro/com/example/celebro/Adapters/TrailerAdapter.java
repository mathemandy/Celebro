package celebro.com.example.celebro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import celebro.com.example.celebro.Models.TrailerItem;
import celebro.com.example.celebro.R;

/**
 * Created by 502575517 on 3/9/2016.
 */
public class TrailerAdapter extends BaseAdapter {
    List<TrailerItem> items = new ArrayList<TrailerItem>();
    private Context context;

    public TrailerAdapter(Context mContext) {
        this.context = mContext;
    }

    public void setData(List<TrailerItem> newTrailers) {
        this.items = newTrailers;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).getSource();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        TrailerViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.trailer_item_row, parent, false);

            holder = new TrailerViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (TrailerViewHolder) convertView.getTag();
        }

        String name = "";

        try {
            name = items.get(position).getName();
        } catch (NullPointerException e) {
            name = "no Name";
        }

        holder.mTrailerName.setText(name);
        return convertView;
    }

    public class TrailerViewHolder {
        TextView mTrailerName;

        public TrailerViewHolder(View view) {
            mTrailerName = (TextView) view.findViewById(R.id.trailer_name);
        }
    }
}
