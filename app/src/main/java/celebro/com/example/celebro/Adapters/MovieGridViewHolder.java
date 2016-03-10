package celebro.com.example.celebro.Adapters;

import android.view.View;
import android.widget.ImageView;

import celebro.com.example.celebro.R;

/**
 * Created by 502575517 on 2/11/2016.
 */
public class MovieGridViewHolder {
    public ImageView mMoviePoster;
    public MovieGridViewHolder(View view){
        mMoviePoster = (ImageView)view.findViewById(R.id.image_view);
    }
}
