package celebro.com.example.celebro.Utils;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import celebro.com.example.celebro.Fragments.SettingsFragment;
import celebro.com.example.celebro.R;

/**
 * Created by 502575517 on 3/7/2016.
 */
public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();

        PreferenceManager.setDefaultValues(SettingsActivity.this, R.xml.pref_general, false);



    }

}
