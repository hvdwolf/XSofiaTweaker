package org.hvdw.xsofiatweaker;

import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.app.AndroidAppHelper;


import de.robv.android.xposed.XposedBridge;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

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
import static org.hvdw.xsofiatweaker.SettingsFragment.EJECT_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.EJECT_CALL_ENTRY;
import static org.hvdw.xsofiatweaker.SettingsFragment.EQ_CALL_OPTION;
import static org.hvdw.xsofiatweaker.SettingsFragment.EQ_CALL_ENTRY;



public class XSofiaTweaker implements IXposedHookZygoteInit, IXposedHookLoadPackage {
	public static final String TAG = "XSofiaTweaker";
	public static Context mContext;
	private static PackageManager pm;
	public static XSharedPreferences pref;

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
	private String eject_call_option;
	private String eject_call_entry;
	private String eq_call_option;
	private String eq_call_entry;


	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
		sharedPreferences.makeWorldReadable();

		noKillEnabled = sharedPreferences.getBoolean(PREF_NO_KILL, true);
		skip_ch_four = sharedPreferences.getBoolean(PREF_SKIP_CH_FOUR, false);
		bt_phone_call_option = sharedPreferences.getString(BT_PHONE_CALL_OPTION, "");
		bt_phone_call_entry = sharedPreferences.getString(BT_PHONE_CALL_ENTRY, "");
		navi_call_option = sharedPreferences.getString(NAVI_CALL_OPTION, "");
		navi_call_entry = sharedPreferences.getString(NAVI_CALL_ENTRY, "");
		band_call_option = sharedPreferences.getString(BAND_CALL_OPTION, "");
		band_call_entry = sharedPreferences.getString(BAND_CALL_ENTRY, "");
		media_call_option = sharedPreferences.getString(MEDIA_CALL_OPTION, "");
		media_call_entry = sharedPreferences.getString(MEDIA_CALL_ENTRY, "");
		dvd_call_option = sharedPreferences.getString(DVD_CALL_OPTION, "");
		dvd_call_entry = sharedPreferences.getString(DVD_CALL_ENTRY, "");
		eject_call_option = sharedPreferences.getString(EJECT_CALL_OPTION, "");
		eject_call_entry = sharedPreferences.getString(EJECT_CALL_ENTRY, "");
		eq_call_option = sharedPreferences.getString(EQ_CALL_OPTION, "");
		eq_call_entry = sharedPreferences.getString(EQ_CALL_ENTRY, "");
	}


	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
		XposedBridge.log(TAG + " Loaded app: " + lpparam.packageName);

		if (!lpparam.packageName.equals("com.syu.ms")) return;

/**********************************************************************************************************************************************/
		/* This is the No Kill function */
		if (noKillEnabled == true) {
			findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "killAppWhenSleep", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					XposedBridge.log(TAG + " nokill enabled");
					param.setResult(null);
				}
			});
		} else {
			XposedBridge.log(TAG + " nokill disabled");
		}


		/* This should prevent the mute of audio channel 4 (alarm) which is used by Google voice for voice feedback 
		*  This seems like a must-do switch on setting, but when no other channel is used it will give noise, although 
		*  you won't hear that with the engine on */
		if (skip_ch_four == true) {
			findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "setStreamVol", int.class, int.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					int stream = (int) param.args[0];
					if (stream == 4) {
						XposedBridge.log(TAG + " skipping alarm channel 4 mute");
						Log.d(TAG, " skipping alarm channel 4 mute");
						param.setResult(null);
					}
				}
			});
		}

/**********************************************************************************************************************************************/
		/* Below are the captured key functions */
		findAndHookMethod("app.HandlerApp", lpparam.classLoader, "wakeup", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				XposedBridge.log(TAG + " Execute the RESUME action to the launcher.sh");
				Log.d(TAG, "Execute the RESUME action to the launcher.sh");
				onItemSelectedp(99);
			}
		});


		if ((bt_phone_call_option != "") && (bt_phone_call_entry != "")) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyBtPhone", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XposedBridge.log(TAG + " mcuKeyBtPhone pressed; bt_phone_call_option: " + bt_phone_call_option + " bt_phone_call_entry : " + bt_phone_call_entry);
					whichActionToPerform(context, navi_call_option, navi_call_entry);

					param.setResult(null);
				}
			});
		}

		if ((navi_call_option != "") && (navi_call_entry != "")) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyNavi", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XposedBridge.log(TAG + " mcuKeyNavi  pressed; navi_call_option: " + navi_call_option + " navi_call_entry : " + navi_call_entry);
					whichActionToPerform(context, navi_call_option, navi_call_entry);

					param.setResult(null);
				}
			});
		}

		if ((band_call_option != "") && (band_call_entry != "")) {
			// band button = radio
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyBand", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XposedBridge.log(TAG + " mcuKeyBand (Radio) pressed; forward action to specific call method");
					Log.d(TAG, "mcuKeyBand (Radio) pressed; forward action to specific call method");
					//startActivityByPackageName(context, band_call_entry);
					//startActivityByPackageName(context, "com.syu.radio");
					XposedBridge.log(TAG + " band_call_option: " + band_call_option + " band_call_entry : " + band_call_entry);
					whichActionToPerform(context, band_call_option, band_call_entry);
					//whichActionToPerform( "band_call_option", "band_key_entry");

					param.setResult(null);
				}
			});
		}

		findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyMode", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				XposedBridge.log(TAG + " Source/Mode pressed; forward action  to the launcher.sh");
				Log.d(TAG, "Source/Mode pressed; forward action  to the launcher.sh");
				onItemSelectedp(37);
				param.setResult(null);
			}
		});


		if ((media_call_option != "") && (media_call_entry != "")) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyPlayer", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XposedBridge.log(TAG + " MEDIA button pressed; forward action to specific call method");
					Log.d(TAG, "MEDIA button pressed; forward action to specific call method");
					//startActivityByPackageName(context, media_call_entry);
					whichActionToPerform(context, media_call_option, media_call_entry);
					//whichActionToPerform( "media_call_option", "media_key_entry");
					param.setResult(null);
				}
			});
		}

		if ((eq_call_option != "") && (eq_call_entry != "")) {
			findAndHookMethod("util.JumpPage", lpparam.classLoader, "eq", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XposedBridge.log(TAG + " EQ button pressed; forward action  to specific call method");
					Log.d(TAG, "EQ button pressed; forward action  to specific call method");
					whichActionToPerform(context, eq_call_option, eq_call_entry);
					param.setResult(null);
				}
			});
		}

		findAndHookMethod("dev.ReceiverMcu", lpparam.classLoader, "onHandle", byte[].class, int.class, int.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				Context context = (Context) AndroidAppHelper.currentApplication();
				//byte[] data  = getByteField(param.thisObject, "byte[].class");
				byte[] data =  (byte[]) param.args[0];
				/* int start = getIntField(param.thisObject, "start");
				int length = getIntField(param.thisObject, "length"); */
				int start = (int) param.args[1];
				int length = (int) param.args[2];
				byte b = data[start];

				//Log.d(TAG, "DVD or eject button; Executed the Media action to the launcher.sh");
				if ((b & 255) == 1 && (data[start + 1] & 255) == 0 && (data[start + 2] & 255) == 16 && (data[start + 3] & 255) == 80) {
					XposedBridge.log(TAG + " DVD button pressed; forward action to specific call method");
					Log.d(TAG, "DVD button pressed; forward action to specific call method");
					whichActionToPerform(context, dvd_call_option, dvd_call_entry);
				}
				if ((b & 255) == 1 && (data[start + 1] & 255) == 161 && (data[start + 2] & 255) == 2 && (data[start + 3] & 255) == 91) {
					XposedBridge.log(TAG + " EJECT button pressed; forward action to specific call method");
					Log.d(TAG, "EJECT button pressed; forward action to specific call method");
					whichActionToPerform(context, eject_call_option, eject_call_entry);
				}
			}
		});

		findAndHookMethod("util.JumpPage", lpparam.classLoader, "broadcastByIntentName", String.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				String actionName = (String) param.args[0];
				XposedBridge.log(TAG + " broadcastByIntentName in util.JumpPage afterHooked " + actionName);
				Log.d(TAG, "broadcastByIntentName in util.JumpPage afterHooked " + actionName);
				if (actionName == "com.glsx.boot.ACCON") {
					Log.d(TAG, "ACC_ON command received");
					XposedBridge.log(TAG + " ACC_ON command received");
					onItemSelectedp(97);
				}
				if (actionName == "com.glsx.boot.ACCOFF") {
					Log.d(TAG, "ACC_OFF command received");
					XposedBridge.log(TAG + " ACC_OFF command received");
					onItemSelectedp(98);
				}
			}
		});

		findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "keyHome", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				XposedBridge.log(TAG + " HOME button pressed; forward action  to the launcher.sh");
				Log.d(TAG, "HOME button pressed; forward action  to the launcher.sh");
				//onItemSelectedp(3);
				//Context context = (Context) AndroidAppHelper.currentApplication();
				//startActivityByPackageName(context, "com.google.android.googlequicksearchbox"); // Google Voice Search
				executeSystemCall("am start -a android.intent.action.MAIN -c android.intent.category.HOME");
				param.setResult(null);
			}
		});

/*		findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "keyBack", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				XposedBridge.log(TAG + " BACK button pressed; forward action  to the launcher.sh");
				Log.d(TAG, "BACK button pressed; forward action  to the launcher.sh");
				onItemSelectedp(4);
				param.setResult(null);
			}
		}); */

	}
	/* End of the handleLoadPackage function doing the capture key functions */
/**********************************************************************************************************************************************/

	private static void onItemSelectedp(int input) {
		StringBuffer output = new StringBuffer();
		String cmd = "/data/launcher.sh " + input + " ";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			Log.d("MCUKEY", cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public void whichActionToPerform (Context context, String callMethod, String actionString) {
		XposedBridge.log(TAG + " WhichActionToPerform: Call method: " + callMethod + " actionString: " + actionString);
		if (callMethod.equals("pkgname")) {
			//Log.d(TAG, " the callmethond is indeed pkgname");
			startActivityByPackageName(context, actionString);
		}
		if (callMethod.equals("pkg_intent")) {
			startActivityByIntentName(context, actionString);
		}
		if (callMethod.equals("sys_call")) {
			executeSystemCall(actionString);
		}
		if (callMethod.equals("broad_cast")) {
			executeBroadcast(actionString);
		}
	};


	private static void executeSystemCall(String input) {
		StringBuffer output = new StringBuffer();
		String cmd = input;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			Log.d(TAG, cmd);
			XposedBridge.log(TAG + ": " + cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	private static void executeBroadcast(String input) {
		StringBuffer output = new StringBuffer();
		String cmd = "am broadcast -a " + input;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			Log.d(TAG, cmd);
			XposedBridge.log(TAG + ": " + cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public void startActivityByIntentName(Context context, String component) {
		Intent sIntent = new Intent(Intent.ACTION_MAIN);
		sIntent.setComponent(ComponentName.unflattenFromString(component));
		sIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(sIntent);
	}


	public void startActivityByPackageName(Context context, String packageName) {
		PackageManager pManager = context.getPackageManager();
		Intent intent = pManager.getLaunchIntentForPackage(packageName);
		XposedBridge.log(TAG + " startActivityByPackageName: " + packageName);
		if (intent != null) {
			context.startActivity(intent);
		}
	}

}
