package org.hvdw.xsofiatweaker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    /* All the necessary variables */
    public static final String PREF_NO_KILL = "pref_no_kill";
    public static final String ACTION_PREF_NO_KILL_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_PREF_NO_KILL_CHANGED";
    public static final String EXTRA_PREF_NO_KILL_ENABLED = "org.hvdw.xsofiatweaker.extra.PREF_NO_KILL_ENABLED";

    public static final String PREF_SKIP_CH_FOUR = "pref_skip_channel_four";
    public static final String ACTION_PREF_SKIP_CH_FOUR_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_PREF_SKIP_CH_FOUR_CHANGED";
    public static final String EXTRA_PREF_SKIP_CH_FOUR_ENABLED = "org.hvdw.xsofiatweaker.extra.PREF_SKIP_CH_FOUR_ENABLED";

    public static final String NAVI_CALL_OPTION = "navi_key_call_option";
    public static final String ACTION_NAVI_CALL_OPTION_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_NAVI_CALL_OPTION_CHANGED";
    public static final String EXTRA_NAVI_CALL_OPTION_STRING = "org.hvdw.xsofiatweaker.extra.PREF_NAVI_CALL_OPTION_STRING";
    public static final String NAVI_CALL_ENTRY = "navi_key_entry";
    public static final String ACTION_NAVI_CALL_ENTRY_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_NAVI_CALL_ENTRY_CHANGED";
    public static final String EXTRA_NAVI_CALL_ENTRY_STRING = "org.hvdw.xsofiatweaker.extra.PREF_NAVI_CALL_ENTRY_STRING";

    public static final String MEDIA_CALL_OPTION = "media_key_call_option";
    public static final String ACTION_MEDIA_CALL_OPTION_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_MEDIA_CALL_OPTION_CHANGED";
    public static final String EXTRA_MEDIA_CALL_OPTION_STRING = "org.hvdw.xsofiatweaker.extra.PREF_MEDIA_CALL_OPTION_STRING";
    public static final String MEDIA_CALL_ENTRY = "media_key_entry";
    public static final String ACTION_MEDIA_CALL_ENTRY_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_MEDIA_CALL_ENTRY_CHANGED";
    public static final String EXTRA_MEDIA_CALL_ENTRY_STRING = "org.hvdw.xsofiatweaker.extra.PREF_MEDIA_CALL_ENTRY_STRING";

    public static final String BT_PHONE_CALL_OPTION = "bt_phone_key_call_option";
    public static final String ACTION_BT_PHONE_CALL_OPTION_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_BT_PHONE_CALL_OPTION_CHANGED";
    public static final String EXTRA_BT_PHONE_CALL_OPTION_STRING = "org.hvdw.xsofiatweaker.extra.PREF_BT_PHONE_CALL_OPTION_STRING";
    public static final String BT_PHONE_CALL_ENTRY = "bt_phone_key_entry";
    public static final String ACTION_BT_PHONE_CALL_ENTRY_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_BT_PHONE_CALL_ENTRY_CHANGED";
    public static final String EXTRA_BT_PHONE_CALL_ENTRY_STRING = "org.hvdw.xsofiatweaker.extra.PREF_BT_PHONE_CALL_ENTRY_STRING";

    public static final String BAND_CALL_OPTION = "band_key_call_option";
    public static final String ACTION_BAND_CALL_OPTION_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_BAND_CALL_OPTION_CHANGED";
    public static final String EXTRA_BAND_CALL_OPTION_STRING = "org.hvdw.xsofiatweaker.extra.PREF_BAND_CALL_OPTION_STRING";
    public static final String BAND_CALL_ENTRY = "band_key_entry";
    public static final String ACTION_BAND_CALL_ENTRY_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_BAND_CALL_ENTRY_CHANGED";
    public static final String EXTRA_BAND_CALL_ENTRY_STRING = "org.hvdw.xsofiatweaker.extra.PREF_BAND_CALL_ENTRY_STRING";

    public static final String DVD_CALL_OPTION = "dvd_key_call_option";
    public static final String ACTION_DVD_CALL_OPTION_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_DVD_CALL_OPTION_CHANGED";
    public static final String EXTRA_DVD_CALL_OPTION_STRING = "org.hvdw.xsofiatweaker.extra.PREF_DVD_CALL_OPTION_STRING";
    public static final String DVD_CALL_ENTRY = "dvd_key_entry";
    public static final String ACTION_DVD_CALL_ENTRY_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_DVD_CALL_ENTRY_CHANGED";
    public static final String EXTRA_DVD_CALL_ENTRY_STRING = "org.hvdw.xsofiatweaker.extra.PREF_DVD_CALL_ENTRY_STRING";

    public static final String EQ_CALL_OPTION = "eq_key_call_option";
    public static final String ACTION_EQ_CALL_OPTION_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_EQ_CALL_OPTION_CHANGED";
    public static final String EXTRA_EQ_CALL_OPTION_STRING = "org.hvdw.xsofiatweaker.extra.PREF_EQ_CALL_OPTION_STRING";
    public static final String EQ_CALL_ENTRY = "eq_key_entry";
    public static final String ACTION_EQ_CALL_ENTRY_CHANGED = "org.hvdw.xsofiatweaker.action.ACTION_EQ_CALL_ENTRY_CHANGED";
    public static final String EXTRA_EQ_CALL_ENTRY_STRING = "org.hvdw.xsofiatweaker.extra.PREF_EQ_CALL_ENTRY_STRING";
    /* End of all the necessary variables */


    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Intent intent = new Intent();

        switch (key) {
            case PREF_NO_KILL:
                intent.setAction(ACTION_PREF_NO_KILL_CHANGED);
                intent.putExtra(EXTRA_PREF_NO_KILL_ENABLED, sharedPreferences.getBoolean(key, false));
                break;
            case PREF_SKIP_CH_FOUR:
                intent.setAction(ACTION_PREF_SKIP_CH_FOUR_CHANGED);
                intent.putExtra(EXTRA_PREF_SKIP_CH_FOUR_ENABLED, sharedPreferences.getBoolean(key, false));
                break;
	    case NAVI_CALL_OPTION:
                intent.setAction(ACTION_NAVI_CALL_OPTION_CHANGED);
                intent.putExtra(EXTRA_NAVI_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case NAVI_CALL_ENTRY:
                intent.setAction(ACTION_NAVI_CALL_ENTRY_CHANGED);
                intent.putExtra(EXTRA_NAVI_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MEDIA_CALL_OPTION:
                intent.setAction(ACTION_MEDIA_CALL_OPTION_CHANGED);
                intent.putExtra(EXTRA_MEDIA_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MEDIA_CALL_ENTRY:
                intent.setAction(ACTION_MEDIA_CALL_ENTRY_CHANGED);
                intent.putExtra(EXTRA_MEDIA_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case BT_PHONE_CALL_OPTION:
                intent.setAction(ACTION_BT_PHONE_CALL_OPTION_CHANGED);
                intent.putExtra(EXTRA_BT_PHONE_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case BT_PHONE_CALL_ENTRY:
                intent.setAction(ACTION_BT_PHONE_CALL_ENTRY_CHANGED);
                intent.putExtra(EXTRA_BT_PHONE_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case BAND_CALL_OPTION:
                intent.setAction(ACTION_BAND_CALL_OPTION_CHANGED);
                intent.putExtra(EXTRA_BAND_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case BAND_CALL_ENTRY:
                intent.setAction(ACTION_BAND_CALL_ENTRY_CHANGED);
                intent.putExtra(EXTRA_BAND_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case DVD_CALL_OPTION:
                intent.setAction(ACTION_DVD_CALL_OPTION_CHANGED);
                intent.putExtra(EXTRA_DVD_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case DVD_CALL_ENTRY:
                intent.setAction(ACTION_DVD_CALL_ENTRY_CHANGED);
                intent.putExtra(EXTRA_DVD_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case EQ_CALL_OPTION:
                intent.setAction(ACTION_EQ_CALL_OPTION_CHANGED);
                intent.putExtra(EXTRA_EQ_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case EQ_CALL_ENTRY:
                intent.setAction(ACTION_EQ_CALL_ENTRY_CHANGED);
                intent.putExtra(EXTRA_EQ_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
        }

        if (intent.getAction() != null) {
            getActivity().sendBroadcast(intent);
        }
    }
}
