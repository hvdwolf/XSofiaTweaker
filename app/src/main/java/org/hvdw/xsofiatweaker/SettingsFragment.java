package org.hvdw.xsofiatweaker;

import android.util.Log;
//import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
import android.os.Bundle;
//import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceFragment;
//import android.preference.PreferenceManager;
//import android.preference.PreferenceActivity;
//import android.preference.ListPreference;
import android.widget.Toast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;


public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    Context mContext;

    private BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter = new IntentFilter();

    @Override

    public void onAttach(Activity activity) {
//    public void onAttach(Context mContext) {
//        super.onAttach(mContext);
	super.onAttach(activity);
        mContext = activity;

	//getPackages(mContext);
    }

    public static final String TAG = "XSofiaTweaker-SettingsFragment";



    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
	getActivity().registerReceiver(broadcastReceiver, intentFilter);

	//getPackages(mContext);
    }

    @Override
    public void onResume() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	getActivity().registerReceiver(broadcastReceiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Intent intent = new Intent();
	    String toastText = "";

        switch (key) {
            /* All the settings belonging to the SofiaServer */
            case MySettings.PREF_NO_KILL:
                intent.setAction(MySettings.ACTION_PREF_NO_KILL_CHANGED);
                intent.putExtra(MySettings.EXTRA_PREF_NO_KILL_ENABLED, sharedPreferences.getBoolean(key, false));
		toastText = "BOOLEAN_KEY";
                break;
            case MySettings.PREF_SKIP_CH_FOUR:
                intent.setAction(MySettings.ACTION_PREF_SKIP_CH_FOUR_CHANGED);
                intent.putExtra(MySettings.EXTRA_PREF_SKIP_CH_FOUR_ENABLED, sharedPreferences.getBoolean(key, false));
		toastText = "BOOLEAN_KEY";
                break;
	    case MySettings.BAND_CALL_OPTION:
                intent.setAction(MySettings.ACTION_BAND_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_BAND_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.BAND_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_BAND_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_BAND_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.BT_PHONE_CALL_OPTION:
                intent.setAction(MySettings.ACTION_BT_PHONE_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_BT_PHONE_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.BT_PHONE_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_BT_PHONE_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_BT_PHONE_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.DVD_CALL_OPTION:
                intent.setAction(MySettings.ACTION_DVD_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_DVD_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.DVD_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_DVD_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_DVD_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.EJECT_CALL_OPTION:
                intent.setAction(MySettings.ACTION_EJECT_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_EJECT_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.EJECT_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_EJECT_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_EJECT_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.EQ_CALL_OPTION:
                intent.setAction(MySettings.ACTION_EQ_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_EQ_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.EQ_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_EQ_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_EQ_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.MEDIA_CALL_OPTION:
                intent.setAction(MySettings.ACTION_MEDIA_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_MEDIA_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.MEDIA_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_MEDIA_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_MEDIA_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.MODE_SRC_CALL_OPTION:
                intent.setAction(MySettings.ACTION_MODE_SRC_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_MODE_SRC_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.MODE_SRC_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_MODE_SRC_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_MODE_SRC_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.NAVI_CALL_OPTION:
                intent.setAction(MySettings.ACTION_NAVI_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_NAVI_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.NAVI_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_NAVI_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_NAVI_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.ACC_ON_CALL_OPTION:
                intent.setAction(MySettings.ACTION_ACC_ON_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_ACC_ON_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.ACC_ON_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_ACC_ON_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_ACC_ON_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.ACC_OFF_CALL_OPTION:
                intent.setAction(MySettings.ACTION_ACC_OFF_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_ACC_OFF_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.ACC_OFF_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_ACC_OFF_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_ACC_OFF_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.RESUME_CALL_OPTION:
                intent.setAction(MySettings.ACTION_RESUME_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_RESUME_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.RESUME_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_RESUME_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_RESUME_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.BACK_KEY_CAPTURE:
                intent.setAction(MySettings.ACTION_BACK_KEY_CAPTURE_CHANGED);
                intent.putExtra(MySettings.EXTRA_BACK_KEY_CAPTURE_STRING, sharedPreferences.getBoolean(key, true));
		toastText = "BOOLEAN_KEY";
                break;
	    case MySettings.BACK_CALL_OPTION:
                intent.setAction(MySettings.ACTION_BACK_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_BACK_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.BACK_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_BACK_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_BACK_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.HOME_KEY_CAPTURE:
                intent.setAction(MySettings.ACTION_HOME_KEY_CAPTURE_CHANGED);
                intent.putExtra(MySettings.EXTRA_HOME_KEY_CAPTURE_STRING, sharedPreferences.getBoolean(key, true));
		toastText = "BOOLEAN_KEY";
                break;
	    case MySettings.HOME_CALL_OPTION:
                intent.setAction(MySettings.ACTION_HOME_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_HOME_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.HOME_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_HOME_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_HOME_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.MUTE_KEY_CAPTURE:
                intent.setAction(MySettings.ACTION_MUTE_KEY_CAPTURE_CHANGED);
                intent.putExtra(MySettings.EXTRA_MUTE_KEY_CAPTURE_STRING, sharedPreferences.getBoolean(key, true));
		toastText = "BOOLEAN_KEY";
                break;
	    case MySettings.MUTE_CALL_OPTION:
                intent.setAction(MySettings.ACTION_MUTE_CALL_OPTION_CHANGED);
                intent.putExtra(MySettings.EXTRA_MUTE_CALL_OPTION_STRING, sharedPreferences.getString(key, ""));
                break;
	    case MySettings.MUTE_CALL_ENTRY:
                intent.setAction(MySettings.ACTION_MUTE_CALL_ENTRY_CHANGED);
                intent.putExtra(MySettings.EXTRA_MUTE_CALL_ENTRY_STRING, sharedPreferences.getString(key, ""));
                break;
            /* The keys from the CANbus apk */
            case MySettings.PREF_DISABLE_AIRHELPER:
                intent.setAction(MySettings.ACTION_PREF_DISABLE_AIRHELPER_CHANGED);
                intent.putExtra(MySettings.EXTRA_PREF_DISABLE_AIRHELPER_ENABLED, sharedPreferences.getBoolean(key, false));
		toastText = "BOOLEAN_KEY";
                break;
            case MySettings.PREF_DISABLE_DOORHELPER:
                intent.setAction(MySettings.ACTION_PREF_DISABLE_DOORHELPER_CHANGED);
                intent.putExtra(MySettings.EXTRA_PREF_DISABLE_DOORHELPER_ENABLED, sharedPreferences.getBoolean(key, false));
		toastText = "BOOLEAN_KEY";
                break;
            default:
                Log.d(TAG, "Invalid setting encountered");
                break;
       }

	    Log.d(TAG, "updated key is " + key);
	    if (toastText.equals("BOOLEAN_KEY")) {
		    toastText = "You updated boolean key \"" + (String)key + "\" to value \"" + String.valueOf(sharedPreferences.getBoolean(key, false)) + "\"";
	    } else {
            Log.d(TAG, "updated string is " + sharedPreferences.getString(key, ""));
            toastText = "You updated key \"" + key + "\" with \"" + sharedPreferences.getString(key, "") + "\"";
        }
        Toast mToast = Toast.makeText(mContext, toastText, Toast.LENGTH_LONG);
	    mToast.show();


        if (intent.getAction() != null) {
		getActivity().sendBroadcast(intent);
        }

    }

}
