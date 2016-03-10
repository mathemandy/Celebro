package celebro.com.example.celebro.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import celebro.com.example.celebro.R;

/**
 * Created by 502575517 on 3/7/2016.
 */
public class Utility {

    public static String getPreferredSortingOrder(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sort_by_key),
                context.getString(R.string.pref_sort_by_default));
    }
}
