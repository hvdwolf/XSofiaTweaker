package org.hvdw.xsofiatweaker;

import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import de.robv.android.xposed.XSharedPreferences;

import static org.hvdw.xsofiatweaker.SettingsFragment.PREF_NO_KILL;
import static org.hvdw.xsofiatweaker.SettingsFragment.PREF_SKIP_CH_FOUR;
import static org.hvdw.xsofiatweaker.SettingsFragment.NAVI_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.NAVI_CALL_ENTRY;
import static org.hvdw.xsofiatweaker.SettingsFragment.MEDIA_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.MEDIA_CALL_ENTRY;
import static org.hvdw.xsofiatweaker.SettingsFragment.BT_PHONE_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.BT_PHONE_CALL_ENTRY;
import static org.hvdw.xsofiatweaker.SettingsFragment.BAND_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.BAND_CALL_ENTRY;
import static org.hvdw.xsofiatweaker.SettingsFragment.DVD_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.DVD_CALL_ENTRY;
import static org.hvdw.xsofiatweaker.SettingsFragment.EQ_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.EQ_CALL_ENTRY;



public class SettingsReceiver extends BroadcastReceiver {

	public static final String TAG = "XSofiaTweaker-SettingsReceiver";


	XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
	//sharedPreferences.makeWorldReadable();
	private String updatedString;

	private boolean noKillEnabled;
	private boolean skip_ch_four;
	private String navi_call_option;
	private String navi_call_entry;
	private String bt_phone_call_option;
	private String bt_phone_call_entry;
	private String band_call_option;
	private String band_call_entry;
	private String media_call_option;
	private String media_call_entry;
	private String dvd_call_option;
	private String dvd_call_entry;
	private String eq_call_option;
	private String eq_call_entry; 


    public void onReceive(Context context, Intent intent) {
	Context mContext = context.getApplicationContext();
        String settingsKey = intent.getAction();
	Toast mToast = Toast.makeText(mContext, "I received " + settingsKey, Toast.LENGTH_LONG);
	//mToast.setGravity(Toast.Gravity.TOP|Toast.Gravity.CENTER_HORIZONTAL, 0, 0);
	mToast.show();
	//Bundle extras = intent.getExtras();
	//String settingsString = intent.getStringExtra

	// Just RELAY the Intent's Action to the SettingsService, which can sort it out.
/*	Intent service = new Intent(context, SettingsService.class);
	service.setAction(intent.getAction());
	service.setExtra(intent.getStringExtra());
	context.startService(service);
*/
/*        if (action.equals("AdBlocker.intent.action.POST_NOTIFICATION")) {
            Bundle extras = intent.getExtras();
            String description = extras.getString("description");
            int id = extras.getInt("id");
            String title = extras.getString("title");
            NotificationUtils.postNotification(title, description, id, context);
        } */
	Log.d(TAG, "received key: " + settingsKey);
	switch (settingsKey) {
            case "ACTION_PREF_NO_KILL_CHANGED":
		//updatedString = extras.getBoolean((boolean) EXTRA_PREF_NO_KILL_ENABLED);
		noKillEnabled = sharedPreferences.getBoolean(PREF_NO_KILL, true);
                break;
            case "ACTION_PREF_SKIP_CH_FOUR_CHANGED":
		skip_ch_four = sharedPreferences.getBoolean(PREF_SKIP_CH_FOUR, false);
                break;
	    case "ACTION_NAVI_CALL_OPTION_CHANGED":
		navi_call_option = sharedPreferences.getString(NAVI_CALL_OPTION, "");
                break;
	    case "ACTION_NAVI_CALL_ENTRY_CHANGED":
		navi_call_entry = sharedPreferences.getString(NAVI_CALL_ENTRY, "");
                break;
	    case "ACTION_MEDIA_CALL_OPTION_CHANGED":
		media_call_option = sharedPreferences.getString(MEDIA_CALL_OPTION, "");
                break;
	    case "ACTION_MEDIA_CALL_ENTRY_CHANGED":
		media_call_entry = sharedPreferences.getString(MEDIA_CALL_ENTRY, "");
                break;
	    case "ACTION_BT_PHONE_CALL_OPTION_CHANGED":
		bt_phone_call_option = sharedPreferences.getString(BT_PHONE_CALL_OPTION, "");
                break;
	    case "ACTION_BT_PHONE_CALL_ENTRY_CHANGED":
		bt_phone_call_entry = sharedPreferences.getString(BT_PHONE_CALL_ENTRY, "");
                break;
	    case "ACTION_BAND_CALL_OPTION_CHANGED":
		band_call_option = sharedPreferences.getString(BAND_CALL_OPTION, "");
                break;
	    case "ACTION_BAND_CALL_ENTRY_CHANGED":
		band_call_entry = sharedPreferences.getString(BAND_CALL_ENTRY, "");
		Log.d(TAG, "ACTION_BAND_CALL_ENTRY: " + band_call_entry);
                break;
	    case "ACTION_DVD_CALL_OPTION_CHANGED":
		dvd_call_option = sharedPreferences.getString(DVD_CALL_OPTION, "");
                break;
	    case "ACTION_DVD_CALL_ENTRY_CHANGED":
		dvd_call_entry = sharedPreferences.getString(DVD_CALL_ENTRY, "");
                break;
	    case "ACTION_EQ_CALL_OPTION_CHANGED":
		eq_call_option = sharedPreferences.getString(EQ_CALL_OPTION, "");
                break;
	    case "ACTION_EQ_CALL_ENTRY_CHANGED":
		eq_call_entry = sharedPreferences.getString(EQ_CALL_ENTRY, "");
                break;
        }

    }
}
