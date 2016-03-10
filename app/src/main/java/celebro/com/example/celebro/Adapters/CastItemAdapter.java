package celebro.com.example.celebro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import celebro.com.example.celebro.Models.CastItem;
import celebro.com.example.celebro.R;
import celebro.com.example.celebro.Utils.CircleTransform;
import celebro.com.example.celebro.Utils.Constants;

/**
 * Created by 502575517 on 3/9/2016.
 */
public class CastItemAdapter extends BaseAdapter {

    List<CastItem> casts = new ArrayList<>();

    private Context mContext;

    public CastItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CastItem> newCast){
        this.casts = newCast;
    }

    @Override
    public int getCount() {
        return casts.isEmpty()? 0: casts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        CastViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate (R.layout.layout_card_with_list_cast, parent, false);

            holder = new CastViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (CastViewHolder) convertView.getTag();
        }
        holder.mActorName.setText(casts.get(position).getActorName());

        String characterAsLabel = mContext.getResources().getString(R.string.character_as_label);

        holder.mCharacter.setText(characterAsLabel + " " + casts.get(position).getCharacter());

        Picasso.with(mContext).load(Constants.THUMB_URL_BASE_PATH+casts.get(position).getProfile_photo()).transform(new CircleTransform()).into(holder.castProfilePhoto);


        return convertView;
    }

    public class CastViewHolder {
        TextView mActorName;
        TextView mCharacter;
        ImageView castProfilePhoto;

        public CastViewHolder(View view) {
            mActorName = (TextView)view.findViewById(R.id.actor_name);
            mCharacter = (TextView)view.findViewById(R.id.character);
            castProfilePhoto = (ImageView) view.findViewById(R.id.profile_photo);

        }
    }
}
