package org.hvdw.xsofiatweaker;

import android.content.Context;
import android.content.SharedPreferences;
import com.crossbowffs.remotepreferences.RemotePreferences;

public class GlobalVars {

    private Context mContext;
    private static SharedPreferences sharedprefs;

    private static GlobalVars ourInstance = new GlobalVars();

    // Getter-Setters
    public static GlobalVars getInstance() {
        return ourInstance;
    }

    public static void setInstance(GlobalVars instance) {
        GlobalVars.ourInstance = ourInstance;
    }

//    private String notification_index;
    private boolean noKillEnabled;
    private boolean UsbDac;  //USB-DAC
    private boolean noMcuErrors;
    private boolean skip_ch_four;
    private boolean disable_airhelper;
    private boolean disable_doorhelper;
    private boolean disable_btphonetop;
    private boolean use_root_access;
    private boolean show_cpu_temp;

    public void Initialize(Context ctxt) {
        mContext = ctxt;
        sharedprefs = new RemotePreferences(mContext, "org.hvdw.xsofiatweaker.preferences.provider", MySettings.SHARED_PREFS_FILENAME);
    }

    private GlobalVars() {
    }


/* Use as:
Declared/Initiaze an instance of class globally in those classes where you want to set/get data (using this code before onCreate() method):-

Globals sharedData = Globals.getInstance();
Set data:

sharedData.setValue("hvdw");
Get data:

String n = sharedData.getValue();
*/
}