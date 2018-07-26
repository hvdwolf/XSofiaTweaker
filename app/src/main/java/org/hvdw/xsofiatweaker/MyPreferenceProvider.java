package org.hvdw.xsofiatweaker;

import com.crossbowffs.remotepreferences.RemotePreferenceProvider;


public class MyPreferenceProvider extends RemotePreferenceProvider {
    public MyPreferenceProvider() {
        super("org.hvdw.xsofiatweaker", new String[] {"org.hvdw.xsofiatweaker_preferences"});
    }
}
