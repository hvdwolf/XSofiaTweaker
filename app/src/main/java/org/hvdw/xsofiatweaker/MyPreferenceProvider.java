package org.hvdw.xsofiatweaker;

import com.crossbowffs.remotepreferences.RemotePreferenceProvider;
import org.hvdw.xsofiatweaker.MySettings;

public class MyPreferenceProvider extends RemotePreferenceProvider {
    public MyPreferenceProvider() {
        super("org.hvdw.xsofiatweaker.preferences.provider", new String[] {"org.hvdw.xsofiatweaker_preferences"});
    }
}