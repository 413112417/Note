package pers.xjh.note.ui.setting;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Button;

import java.util.List;

import pers.xjh.note.R;

/**
 * Created by xjh on 17-12-1.
 */

public class PreferenceTestActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("Exit");
            setListFooter(button);
        }
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        System.out.println(fragmentName);
        return true;
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.perference_header, target);
    }

    public static class Prefs1Fragment extends PreferenceFragment {

    }

    public static class Pref2Fragment extends PreferenceFragment {

    }
}
