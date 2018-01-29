package pers.xjh.note.ui.setting;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import pers.xjh.note.R;

/**
 * Created by xjh on 17-12-1.
 */

public class PreferenceTestFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_screen);

        String name = ((EditTextPreference) findPreference("name")).getText();
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
    }
}
