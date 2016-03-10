package celebro.com.example.celebro.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import celebro.com.example.celebro.Models.MovieItem;
import celebro.com.example.celebro.R;
import celebro.com.example.celebro.Utils.Constants;

/**
 * Created by 502575517 on 2/11/2016.
 */
public class MovieGridAdapter extends BaseAdapter {
    private List<MovieItem> movies = new ArrayList<>();
    private boolean mNotifyOnChange = true;


    private Context mContext;

    public MovieGridAdapter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void swapData(List<MovieItem> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged();
    }

    public void cleanData() {
        this.movies.clear();
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        MovieGridViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            holder = new MovieGridViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MovieGridViewHolder) convertView.getTag();
        }
        String imgUrl = Constants.THUMB_URL_BASE_PATH + movies.get(position).getPosterPath();
//        Set Image using Picasso
        Picasso.with(mContext).load(imgUrl).into(holder.mMoviePoster);

        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }
}
