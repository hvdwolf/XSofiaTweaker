package org.hvdw.xsofiatweaker;

import com.crossbowffs.remotepreferences.RemotePreferenceProvider;


public class MyPreferenceProvider extends RemotePreferenceProvider {
    public MyPreferenceProvider() {
        super("com.dev.weathon.customalertslider", new String[] {"com.dev.weathon.customalertslider_preferences"});
    }
}
