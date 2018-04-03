package org.hvdw.xsofiatweaker;

import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
/*import android.content.SharedPreferences;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo; */
import android.content.ComponentName;
/*import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager; */
import android.app.AndroidAppHelper;
import android.widget.Toast;
/* shellExec and rootExec methods */
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
/* assets filecopy */
/*import android.content.res.AssetManager;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream; */

//CPUTemp
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.widget.TextView;
import android.graphics.Color;

import android.media.AudioManager;  //USB-DAC

import de.robv.android.xposed.XposedBridge;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;
//import de.robv.android.xposed.XC_MethodReplacement;
//import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class XSofiaTweaker implements IXposedHookZygoteInit, IXposedHookLoadPackage {
	public static final String TAG = "XSofiaTweaker";
	public static Context mContext;
	private static PackageManager pm;
	public static XSharedPreferences pref;

	private AudioManager audioManager; //USB-DAC
	private int CurrentVolume; //USB-DAC

	private boolean noKillEnabled;
	private boolean UsbDac;  //USB-DAC
	private boolean noMcuErrors;
	private boolean skip_ch_four;
	private boolean disable_airhelper;
	private boolean disable_doorhelper;
	private boolean disable_btphonetop;
	private boolean use_root_access;
	private boolean show_cpu_temp;
	private boolean display_org_clock = false; // just a helper boolean for show_cpu_temp, not a setting.
	public boolean firstCall = false; // For the "eliminate feedback during the call if you have OK Google anywhere enabled"

	private String band_call_option;
	private String band_call_entry;
	private String bt_phone_call_option;
	private String bt_phone_call_entry;
	private String bt_hang_call_option;
	private String bt_hang_call_entry;
	private String dvd_call_option;
	private String dvd_call_entry;
	private String eject_call_option;
	private String eject_call_entry;
	private String eq_call_option;
	private String eq_call_entry;
	private String media_call_option;
	private String media_call_entry;
	private String mode_src_call_option;
	private String mode_src_call_entry;
	private String navi_call_option;
	private String navi_call_entry;

	private String acc_on_call_option;
	private String acc_on_call_entry;
	private String acc_off_call_option;
	private String acc_off_call_entry;
	private String resume_call_option;
	private String resume_call_entry;

	private boolean home_key_capture_enabled;
	private String home_call_option;
	private String home_call_entry;
	private boolean mute_key_capture_enabled;
	private String mute_call_option;
	private String mute_call_entry;

	private String test_call_option;
	private String test_entry;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		Context mContext = (Context) AndroidAppHelper.currentApplication();
		XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
		sharedPreferences.makeWorldReadable();

		noKillEnabled = sharedPreferences.getBoolean(MySettings.PREF_NO_KILL, true);
		UsbDac = sharedPreferences.getBoolean(MySettings.PREF_UsbDac, false); //USB-DAC
		noMcuErrors = sharedPreferences.getBoolean(MySettings.PREF_NO_MCU_ERRORS, false);
		skip_ch_four = sharedPreferences.getBoolean(MySettings.PREF_SKIP_CH_FOUR, false);
		disable_airhelper = sharedPreferences.getBoolean(MySettings.PREF_DISABLE_AIRHELPER, false);
		disable_doorhelper = sharedPreferences.getBoolean(MySettings.PREF_DISABLE_DOORHELPER, false);
		disable_btphonetop = sharedPreferences.getBoolean(MySettings.PREF_DISABLE_BTPHONETOP, false);
		use_root_access = sharedPreferences.getBoolean(MySettings.USE_ROOT_ACCESS, true);
		show_cpu_temp = sharedPreferences.getBoolean(MySettings.SHOW_CPU_TEMP, false);

		band_call_option = sharedPreferences.getString(MySettings.BAND_CALL_OPTION, "");
		band_call_entry = sharedPreferences.getString(MySettings.BAND_CALL_ENTRY, "");
		bt_phone_call_option = sharedPreferences.getString(MySettings.BT_PHONE_CALL_OPTION, "");
		bt_phone_call_entry = sharedPreferences.getString(MySettings.BT_PHONE_CALL_ENTRY, "");
		bt_hang_call_option = sharedPreferences.getString(MySettings.BT_HANG_CALL_OPTION, "");
		bt_hang_call_entry = sharedPreferences.getString(MySettings.BT_HANG_CALL_ENTRY, "");
		dvd_call_option = sharedPreferences.getString(MySettings.DVD_CALL_OPTION, "");
		dvd_call_entry = sharedPreferences.getString(MySettings.DVD_CALL_ENTRY, "");
		eject_call_option = sharedPreferences.getString(MySettings.EJECT_CALL_OPTION, "");
		eject_call_entry = sharedPreferences.getString(MySettings.EJECT_CALL_ENTRY, "");
		eq_call_option = sharedPreferences.getString(MySettings.EQ_CALL_OPTION, "");
		eq_call_entry = sharedPreferences.getString(MySettings.EQ_CALL_ENTRY, "");
		media_call_option = sharedPreferences.getString(MySettings.MEDIA_CALL_OPTION, "");
		media_call_entry = sharedPreferences.getString(MySettings.MEDIA_CALL_ENTRY, "");
		mode_src_call_option = sharedPreferences.getString(MySettings.MODE_SRC_CALL_OPTION, "");
		mode_src_call_entry = sharedPreferences.getString(MySettings.MODE_SRC_CALL_ENTRY, "");
		navi_call_option = sharedPreferences.getString(MySettings.NAVI_CALL_OPTION, "");
		navi_call_entry = sharedPreferences.getString(MySettings.NAVI_CALL_ENTRY, "");

		acc_on_call_option = sharedPreferences.getString(MySettings.ACC_ON_CALL_OPTION, "");
		acc_on_call_entry = sharedPreferences.getString(MySettings.ACC_ON_CALL_ENTRY, "");
		acc_off_call_option = sharedPreferences.getString(MySettings.ACC_OFF_CALL_OPTION, "");
		acc_off_call_entry = sharedPreferences.getString(MySettings.ACC_OFF_CALL_ENTRY, "");
		resume_call_option = sharedPreferences.getString(MySettings.RESUME_CALL_OPTION, "");
		resume_call_entry = sharedPreferences.getString(MySettings.RESUME_CALL_ENTRY, "");

		home_key_capture_enabled = sharedPreferences.getBoolean(MySettings.HOME_KEY_CAPTURE, true);
		home_call_option = sharedPreferences.getString(MySettings.HOME_CALL_OPTION, "");
		home_call_entry = sharedPreferences.getString(MySettings.HOME_CALL_ENTRY, "");
		mute_key_capture_enabled = sharedPreferences.getBoolean(MySettings.MUTE_KEY_CAPTURE, true);
		mute_call_option = sharedPreferences.getString(MySettings.MUTE_CALL_OPTION, "");
		mute_call_entry = sharedPreferences.getString(MySettings.MUTE_CALL_ENTRY, "");

		// check our assets file and copy to /sdcard/XSofiaTweaker if necessary
        /*Log.d(TAG, "copying navi_app.txt");
        UtilsActivity.CheckCopyAssetFile(mContext, "navi_app.txt");
        Log.d(TAG, "copying player_app.txt");
        UtilsActivity.CheckCopyAssetFile(mContext, "player_app.txt"); */

	}


	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
	   XposedBridge.log(TAG + " Loaded app: " + lpparam.packageName);

//	   if (!lpparam.packageName.equals("com.syu.ms")) return;
	   if (lpparam.packageName.equals("com.syu.ms")) {

/**********************************************************************************************************************************************/

		// Hook for the "eliminate feedback during the call if you have OK Google anywhere enabled"
		Class<?> ProfileInfoClass = XposedHelpers.findClass("module.bt.HandlerBt", lpparam.classLoader);
			XposedBridge.hookAllMethods(ProfileInfoClass, "btPhoneState", new XC_MethodHook() {
			public void beforeHookedMethod(MethodHookParam param) throws Throwable {
				int phonestate = (int) param.args[0];
				//5 is in call
				//4 is ringing?
				//3 is dialing
				//2 is idle?
				//1 is ?
				//0 is n/c?

				//XposedBridge.log("BTTEST " + "phonestate:" + phonestate);
				if (phonestate == 3 || phonestate == 4) {
					firstCall = true;
					sudoVoiceKill();
				}
				if ((phonestate == 2) && (firstCall == true)) {
					//XposedBridge.log("BTTEST CALL WAS HUNG UP");
					firstCall = false;
					sudoVoiceRestart();
				}
				}
			});
		// End of the hook for the "eliminate feedback during the call if you have OK Google anywhere enabled"


		/* This is the No Kill function */
		if (noKillEnabled == true) {
			findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "killAppWhenSleep", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					noKillEnabled = sharedPreferences.getBoolean(MySettings.PREF_NO_KILL, true);
					XposedBridge.log(TAG + " nokill enabled");
					param.setResult(null);
				}
			});
		} else {
			XposedBridge.log(TAG + " nokill disabled");
		}

		//USB-DAC-START
		if (UsbDac == true) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyVolUp", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					UsbDac = sharedPreferences.getBoolean(MySettings.PREF_UsbDac, true);
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					CurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					audioManager.setStreamVolume(3, CurrentVolume + 1, 1);
					audioManager.setStreamVolume(5, CurrentVolume + 1, 0);
					//XposedBridge.log(TAG + " VolUp");
					param.setResult(null);
				}
			});

			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyVolDown", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					UsbDac = sharedPreferences.getBoolean(MySettings.PREF_UsbDac, true);
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					CurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					audioManager.setStreamVolume(3, CurrentVolume + -1, 1);
					audioManager.setStreamVolume(5, CurrentVolume + -1, 0);
					//XposedBridge.log(TAG + " VolDown");
					param.setResult(null);
				}
			});

			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyVolMute", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					UsbDac = sharedPreferences.getBoolean(MySettings.PREF_UsbDac, true);
					AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
					if (audioManager.isStreamMute(AudioManager.STREAM_MUSIC)) {
						audioManager.adjustStreamVolume(0, 100, 0);
						audioManager.adjustStreamVolume(1, 100, 0);
						audioManager.adjustStreamVolume(2, 100, 0);
						audioManager.adjustStreamVolume(3, 100, 1);
						audioManager.adjustStreamVolume(4, 100, 0);
						audioManager.adjustStreamVolume(5, 100, 0);
						audioManager.adjustStreamVolume(6, 100, 0);
					} else {
						audioManager.adjustStreamVolume(0, 101, 0);
						audioManager.adjustStreamVolume(1, 101, 0);
						audioManager.adjustStreamVolume(2, 101, 0);
						audioManager.adjustStreamVolume(3, 101, 1);
						audioManager.adjustStreamVolume(4, 101, 0);
						audioManager.adjustStreamVolume(5, 101, 0);
						audioManager.adjustStreamVolume(6, 101, 0);
					}
					//XposedBridge.log(TAG + " Mute");
					param.setResult(null);
				}
			});

			findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "setStreamVol", int.class, int.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					UsbDac = sharedPreferences.getBoolean(MySettings.PREF_UsbDac, true);
					//XposedBridge.log(TAG + " Stop setStreamVol");
					param.setResult(null);
				}
			});
		}
		//USB-DAC-END

		if (noMcuErrors == true) {
			findAndHookMethod("ui.InfoView", lpparam.classLoader, "addSelfToWindow", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					noMcuErrors = sharedPreferences.getBoolean(MySettings.PREF_NO_MCU_ERRORS, true);
					XposedBridge.log(TAG + " noMcuErrors enabled");
					param.setResult(null);
				}
			});
		} else {
			XposedBridge.log(TAG + " noMcuErrors disabled");
		}


		/* This should prevent the mute of audio channel 4 (alarm) which is used by Google voice for voice feedback 
		*  This seems like a must-do switch on setting, but when no other channel is used it will give noise, although 
		*  you won't hear that with the engine on */
		if (skip_ch_four == true && UsbDac == false) { //USB-DAC find and replace if (skip_ch_four == true)
			findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "setStreamVol", int.class, int.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					int stream = (int) param.args[0];
					if (stream == 4) {
						Context context = (Context) AndroidAppHelper.currentApplication();
						XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
						sharedPreferences.makeWorldReadable();
						skip_ch_four = sharedPreferences.getBoolean(MySettings.PREF_SKIP_CH_FOUR, false);
						XposedBridge.log(TAG + " skipping alarm channel 4 mute");
						Log.d(TAG, " skipping alarm channel 4 mute");

						param.setResult(null);
					}
				}
			});
		}

		if (disable_btphonetop == true) {
			//Class<?> ProfileInfoClass = XposedHelpers.findClass("module.bt.HandlerBt$3", lpparam.classLoader);
			//No lo nger necessary to declare as it is declared on top in the OK Google function
			ProfileInfoClass = XposedHelpers.findClass("module.bt.HandlerBt$3", lpparam.classLoader);
			   XposedBridge.hookAllMethods(ProfileInfoClass, "topChanged", new XC_MethodHook() {
				   @Override
				   protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					   Context context = (Context) AndroidAppHelper.currentApplication();
					   XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					   sharedPreferences.makeWorldReadable();
					   disable_btphonetop = sharedPreferences.getBoolean(MySettings.PREF_DISABLE_BTPHONETOP, false);
					   XposedBridge.log(TAG + " prevent bt phone app from forcing always on top during call");
					   param.setResult(null);
				   }
			});
		}

		/* Correct the mediaKey function in app/ToolkitApp.java
		*  Gustdens modified code only sends media keys to the active media player
		*  The original code uses an intent and "every" media player listening will react. */
//		findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "mediaKey", int.class, new XC_MethodReplacement() {
/*		findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "mediaKey", int.class, new XC_MethodHook() {
			@Override
			//protected void replaceHookedMethod(XC_MethodHook.MethodHookParam param) {
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				int onKey1 = (int) param.args[0];
				XposedBridge.log(TAG + " Do not call mediaKey(int, int) but call onKey with parameter 1");
				Log.d(TAG, "Do not call mediaKey(int, int) but call onKey with parameter 1");
				Class<?> classstart = XposedHelpers.findClass("app.ToolkitApp", lpparam.classLoader);
				//Object class2Instance = XposedHelpers.newInstance(classstart, onKey1);
				//XposedHelpers.callMethod(class2Instance, "onKey");
				XposedHelpers.callMethod(onKey1, "onKey");
				XposedBridge.log(TAG + " onKey should be done instead of mediaKey" );

				param.setResult(null);
			}
		});

		findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "mediaKey", int.class, int.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				int onKey2 = (int) param.args[1];
				XposedBridge.log(TAG + " Do not call mediaKey(int, int) but call onKey with parameter 2");
				Log.d(TAG, "Do not call mediaKey(int, int) but call onKey with parameter 2");
				Class<?> classstart = XposedHelpers.findClass("app.ToolkitApp", lpparam.classLoader);
				//Object class2Instance = XposedHelpers.newInstance(classstart, onKey2);
				//XposedHelpers.callMethod(class2Instance, "onKey");
				XposedHelpers.callMethod(onKey2, "onKey");
				XposedBridge.log(TAG + " onKey should be done instead of mediaKey" );
				param.setResult(null);
			}
		}); */


/**********************************************************************************************************************************************/
		/* Below are the captured key functions */
		findAndHookMethod("app.HandlerApp", lpparam.classLoader, "wakeup", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				Context context = (Context) AndroidAppHelper.currentApplication();
				XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
				sharedPreferences.makeWorldReadable();
				resume_call_option = sharedPreferences.getString(MySettings.RESUME_CALL_OPTION, "");
				resume_call_entry = sharedPreferences.getString(MySettings.RESUME_CALL_ENTRY, "");
				XposedBridge.log(TAG + " Execute the RESUME action using specific call method");
				Log.d(TAG, "Execute the RESUME action using specific call method");
			}
		});


		if ((bt_phone_call_option != "") && (bt_phone_call_entry != "")) {
//			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyBtPhone", new XC_MethodHook() {  Is not correct one ??
			findAndHookMethod("util.JumpPage", lpparam.classLoader, "btPageDialByKey", new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					bt_phone_call_option = sharedPreferences.getString(MySettings.BT_PHONE_CALL_OPTION, "");
					bt_phone_call_entry = sharedPreferences.getString(MySettings.BT_PHONE_CALL_ENTRY, "");
					XposedBridge.log(TAG + " mcuKeyBtPhone pressed; bt_phone_call_option: " + bt_phone_call_option + " bt_phone_call_entry : " + bt_phone_call_entry);
					whichActionToPerform(context, bt_phone_call_option, bt_phone_call_entry);

					param.setResult(null);
				}
			});
		}

		if ((navi_call_option != "") && (navi_call_entry != "")) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyNavi", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					navi_call_option = sharedPreferences.getString(MySettings.NAVI_CALL_OPTION, "");
					navi_call_entry = sharedPreferences.getString(MySettings.NAVI_CALL_ENTRY, "");
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
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					band_call_option = sharedPreferences.getString(MySettings.BAND_CALL_OPTION, "");
					band_call_entry = sharedPreferences.getString(MySettings.BAND_CALL_ENTRY, "");
					XposedBridge.log(TAG + " mcuKeyBand (Radio) pressed; forward action to specific call method");
					Log.d(TAG, "mcuKeyBand (Radio) pressed; forward action to specific call method");
					XposedBridge.log(TAG + " band_call_option: " + band_call_option + " band_call_entry : " + band_call_entry);
					whichActionToPerform(context, band_call_option, band_call_entry);

					param.setResult(null);
				}
			});
		}

		if ((mode_src_call_option != "") && (mode_src_call_entry != "")) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyMode", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					mode_src_call_option = sharedPreferences.getString(MySettings.MODE_SRC_CALL_OPTION, "");
					mode_src_call_entry = sharedPreferences.getString(MySettings.MODE_SRC_CALL_ENTRY, "");
					XposedBridge.log(TAG + " Source/Mode pressed; forward action  to specific call method");
					Log.d(TAG, "Source/Mode pressed; forward action  to specific call method");
					whichActionToPerform(context, mode_src_call_option, mode_src_call_entry);

					param.setResult(null);
				}
			});
		}

		if ((media_call_option != "") && (media_call_entry != "")) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyPlayer", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					media_call_option = sharedPreferences.getString(MySettings.MEDIA_CALL_OPTION, "");
					media_call_entry = sharedPreferences.getString(MySettings.MEDIA_CALL_ENTRY, "");
					XposedBridge.log(TAG + " MEDIA button pressed; forward action to specific call method");
					Log.d(TAG, "MEDIA button pressed; forward action to specific call method");
					whichActionToPerform(context, media_call_option, media_call_entry);

					param.setResult(null);
				}
			});
		}

		if ((eq_call_option != "") && (eq_call_entry != "")) {
			findAndHookMethod("util.JumpPage", lpparam.classLoader, "eq", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					eq_call_option = sharedPreferences.getString(MySettings.EQ_CALL_OPTION, "");
					eq_call_entry = sharedPreferences.getString(MySettings.EQ_CALL_ENTRY, "");
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

				XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
				sharedPreferences.makeWorldReadable();

				//Log.d(TAG, "DVD or eject button; Executed the Media action to the launcher.sh");
				if ((b & 255) == 1 && (data[start + 1] & 255) == 0 && (data[start + 2] & 255) == 16 && (data[start + 3] & 255) == 80) {
					dvd_call_option = sharedPreferences.getString(MySettings.DVD_CALL_OPTION, "");
					dvd_call_entry = sharedPreferences.getString(MySettings.DVD_CALL_ENTRY, "");
					XposedBridge.log(TAG + " DVD button pressed; forward action to specific call method");
					Log.d(TAG, "DVD button pressed; forward action to specific call method");
					whichActionToPerform(context, dvd_call_option, dvd_call_entry);
				}
				if ((b & 255) == 1 && (data[start + 1] & 255) == 161 && (data[start + 2] & 255) == 2 && (data[start + 3] & 255) == 91) {
					eject_call_option = sharedPreferences.getString(MySettings.EJECT_CALL_OPTION, "");
					eject_call_entry = sharedPreferences.getString(MySettings.EJECT_CALL_ENTRY, "");
					XposedBridge.log(TAG + " EJECT button pressed; forward action to specific call method");
					Log.d(TAG, "EJECT button pressed; forward action to specific call method");
					whichActionToPerform(context, eject_call_option, eject_call_entry);
				}
			}
		});

		if ((bt_hang_call_option != "") && (bt_hang_call_entry != "")) {
			findAndHookMethod("dev.ReceiverMcu", lpparam.classLoader, "onHandleBt", byte[].class, int.class, int.class, new XC_MethodHook() { 
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					byte[] data =  (byte[]) param.args[0];
					int start = (int) param.args[1];
					byte b = data[start];
					byte c = data[start + 1];

					if ((b == 16) && (c == 0)) {
						Context context = (Context) AndroidAppHelper.currentApplication();
						XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
						sharedPreferences.makeWorldReadable();
						bt_hang_call_option = sharedPreferences.getString(MySettings.BT_HANG_CALL_OPTION, "");
						bt_hang_call_entry = sharedPreferences.getString(MySettings.BT_HANG_CALL_ENTRY, "");
						XposedBridge.log(TAG + " BT_Hang pressed; bt_hang_call_option: " + bt_hang_call_option + " bt_hang_call_entry : " + bt_hang_call_entry);
						whichActionToPerform(context, bt_hang_call_option, bt_hang_call_entry);

						param.setResult(null);
					}
				}
			});
		}


		findAndHookMethod("util.JumpPage", lpparam.classLoader, "broadcastByIntentName", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				String actionName = (String) param.args[0];
				Context context = (Context) AndroidAppHelper.currentApplication();
				XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
				sharedPreferences.makeWorldReadable();
				XposedBridge.log(TAG + " broadcastByIntentName in util.JumpPage beforeHooked " + actionName);
				Log.d(TAG, "broadcastByIntentName in util.JumpPage afterHooked " + actionName);
				if (actionName == "com.glsx.boot.ACCON") {
					acc_on_call_option = sharedPreferences.getString(MySettings.ACC_ON_CALL_OPTION, "");
					acc_on_call_entry = sharedPreferences.getString(MySettings.ACC_ON_CALL_ENTRY, "");
					Log.d(TAG, "ACC_ON command received");
					XposedBridge.log(TAG + " ACC_ON command received");
					whichActionToPerform(context, acc_on_call_option, acc_on_call_entry);
				}
				if (actionName == "com.glsx.boot.ACCOFF") {
					acc_off_call_option = sharedPreferences.getString(MySettings.ACC_OFF_CALL_OPTION, "");
					acc_off_call_entry = sharedPreferences.getString(MySettings.ACC_OFF_CALL_ENTRY, "");
					Log.d(TAG, "ACC_OFF command received");
					XposedBridge.log(TAG + " ACC_OFF command received");
					whichActionToPerform(context, acc_off_call_option, acc_off_call_entry);
				}
			}
		});

		if (home_key_capture_enabled == true) {
			findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "keyHome", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					home_call_option = sharedPreferences.getString(MySettings.HOME_CALL_OPTION, "");
					home_call_entry = sharedPreferences.getString(MySettings.HOME_CALL_ENTRY, "");
					XposedBridge.log(TAG + " HOME button pressed; forward action to specific call method");
					Log.d(TAG, "HOME button pressed; forward action to specific call method");
					//executeSystemCall("am start -a android.intent.action.MAIN -c android.intent.category.HOME");
					whichActionToPerform(context, home_call_option, home_call_entry);
					param.setResult(null);
				}
			});
		}

/*		findAndHookMethod("app.ToolkitApp", lpparam.classLoader, "keyBack", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				XposedBridge.log(TAG + " BACK button pressed; forward action  to the launcher.sh");
				Log.d(TAG, "BACK button pressed; forward action  to the launcher.sh");
				onItemSelectedp(4);
				param.setResult(null);
			}
		}); */

		if (mute_key_capture_enabled == true) {
			findAndHookMethod("module.main.HandlerMain", lpparam.classLoader, "mcuKeyVolMute", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					mute_call_option = sharedPreferences.getString(MySettings.MUTE_CALL_OPTION, "");
					mute_call_entry = sharedPreferences.getString(MySettings.MUTE_CALL_ENTRY, "");
					XposedBridge.log(TAG + " MUTE button pressed; forward action to specific call method");
					Log.d(TAG, "MUTE button pressed; forward action to specific call method");
					whichActionToPerform(context, mute_call_option, mute_call_entry);
					param.setResult(null);
				}
			});
		}

	   /* End of the part where the SofiaServer hooks are taking place
	   *  Now starts the part where the keys of the Canbus apk are captured
	   */ 
	   } else if (lpparam.packageName.equals("com.syu.canbus")) {
		if (disable_airhelper == true) {
			findAndHookMethod("com.syu.ui.air.AirHelper", lpparam.classLoader, "showAndRefresh", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					noKillEnabled = sharedPreferences.getBoolean(MySettings.PREF_DISABLE_AIRHELPER, true);
					XposedBridge.log(TAG + " prevent canbus airconditiong change popup");
					param.setResult(null);
				}
			});
		}


		if (disable_doorhelper == true) {
			findAndHookMethod("com.syu.ui.door.DoorHelper", lpparam.classLoader, "showAndRefresh", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
					Context context = (Context) AndroidAppHelper.currentApplication();
					XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
					sharedPreferences.makeWorldReadable();
					noKillEnabled = sharedPreferences.getBoolean(MySettings.PREF_DISABLE_DOORHELPER, true);
					XposedBridge.log(TAG + " prevent canbus door open popup");
					param.setResult(null);
				}
			});
		}

	   /* End of the part where the CANbus apk hooks are taking place
	   *  Nowstarts the part where the SystemUI clock display is captured to display the CPU temp.
	   */
	   } else if ((lpparam.packageName.equals("com.android.systemui")) && (show_cpu_temp == true)) {
		findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				TextView tv = (TextView) param.thisObject;
				String text = tv.getText().toString();
				String temp = String.valueOf(getCpuTemp());
				//remove + in front of string
				temp = temp.replace("+", "");
				tv.setText("CPU: " + temp + " °C  " + text);
				//tv.setTextColor(Color.YELLOW);
				//tv.setTextColor(Color.parseColor("#F06D2F")); // orange
				//tv.setTextColor(Color.RED);
			}

		});
	   /*
	   *  simply return out of the module if no sofiaser or no CANbus or no SystemUI (would be very strange) is detected
	   */
	   } else return;
	}
	/* End of the handleLoadPackage function doing the capture key functions */
/**********************************************************************************************************************************************/

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
			XSharedPreferences sharedPreferences = new XSharedPreferences("org.hvdw.xsofiatweaker");
			sharedPreferences.makeWorldReadable();
			use_root_access = sharedPreferences.getBoolean(MySettings.USE_ROOT_ACCESS, true);
			//executeSystemCall(actionString);
			String[] cmd = actionString.split(";");
			if (use_root_access == true) {
				rootExec(cmd);
			} else {
				shellExec(cmd);
			}
		}
	};


/*	private static void executeSystemCall(String input) {
		String cmd = input;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			Log.d(TAG, cmd);
			XposedBridge.log(TAG + ": " + cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
*/

// Simple versions: a normal shell version and an su version
/*	public static void shellExec(String...strings) {
		try{
			Process sh = Runtime.getRuntime().exec("sh");
			DataOutputStream outputStream = new DataOutputStream(sh.getOutputStream());

			for (String s : strings) {
				s = s.trim();
				outputStream.writeBytes(s+"\n");
				outputStream.flush();
			}

			outputStream.writeBytes("exit\n");
			outputStream.flush();
			try {
				sh.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			outputStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void rootExec(String...strings) {
		try{
			Process su = Runtime.getRuntime().exec("su");
			DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

			for (String s : strings) {
				s = s.trim();
				outputStream.writeBytes(s+"\n");
				outputStream.flush();
			}

			outputStream.writeBytes("exit\n");
			outputStream.flush();
			try {
				su.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			outputStream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
*/

/*  More complicated versions of above shell and su call. As I want to run multiple commands I also need to look at that. 
    copied from https://stackoverflow.com/questions/20932102/execute-shell-command-from-android/26654728
    from the code of CarloCannas
*/
	public static String shellExec(String... strings) {
		String res = "";
		DataOutputStream outputStream = null;
		InputStream response = null;
		try {
			Process sh = Runtime.getRuntime().exec("sh");
			outputStream = new DataOutputStream(sh.getOutputStream());
			response = sh.getInputStream();

			for (String s : strings) {
				s = s.trim();
				outputStream.writeBytes(s + "\n");
				outputStream.flush();
			}

			outputStream.writeBytes("exit\n");
			outputStream.flush();
			try {
				sh.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			res = readFully(response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Closer.closeSilently(outputStream, response);
		}
		return res;
	}


	public static String rootExec(String... strings) {
		String res = "";
		DataOutputStream outputStream = null;
		InputStream response = null;
		try {
			Process su = Runtime.getRuntime().exec("su");
			outputStream = new DataOutputStream(su.getOutputStream());
			response = su.getInputStream();

			for (String s : strings) {
				s = s.trim();
				outputStream.writeBytes(s + "\n");
				outputStream.flush();
			}

			outputStream.writeBytes("exit\n");
			outputStream.flush();
			try {
				su.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			res = readFully(response);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Closer.closeSilently(outputStream, response);
		}
		return res;
	}

	public static String readFully(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = is.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos.toString("UTF-8");
	}


	private static void executeScript(String input) {
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

	public float getCpuTemp() {
		Process p;
		float temp = 0;
		try {
			for (int i=1; i<5; i++) {
				p = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone" + Integer.toString(i) + "/temp");
				p.waitFor();
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line = reader.readLine();
				float tmptemp = Float.parseFloat(line) / 1000.0f;
				if (tmptemp > temp) temp = tmptemp;
			}
			// round to 1 decimal like 45.3
			int scale = (int) Math.pow(10, 1);
			return (float) Math.round(temp *scale)/scale;

		} catch (Exception e) {
			e.printStackTrace();
			return 0.0f;
		}
	}

	// Methods for the "eliminate feedback during the call if you have OK Google anywhere enabled"
	public void sudoVoiceKill(){
		try {
			Process suProcess = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			//XposedBridge.log("BTTEST new su kill");
			os.writeBytes("am force-stop com.google.android.googlequicksearchbox" + "\n");
			os.flush();
			os.writeBytes("pm revoke com.google.android.googlequicksearchbox android.permission.RECORD_AUDIO" + "\n");
			os.flush();
		}
		catch (Exception e) {
			RuntimeException ex = new RuntimeException("Unable to kill Google Voice Search: " + e.getMessage());
			throw ex;
		}
	}


	public void sudoVoiceRestart(){
		try {
			Process suProcess = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			//XposedBridge.log("BTTEST new su start");
			os.writeBytes("pm grant com.google.android.googlequicksearchbox android.permission.RECORD_AUDIO" + "\n");
			os.flush();
			os.writeBytes("am start com.google.android.googlequicksearchbox" + "\n");
			os.flush();
		}
		catch (Exception e) {
			RuntimeException ex = new RuntimeException("Unable to start Google Voice Search: " + e.getMessage());
			throw ex;
		}
	}
	// End of methods for the "eliminate feedback during the call if you have OK Google anywhere enabled"

}
