package org.hvdw.xsofiatweaker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/*import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;*/

import android.preference.ListPreference;
import android.util.AttributeSet;
import android.preference.PreferenceFragment;

import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceActivity;

import android.content.SharedPreferences;
//import android.categories.Category;


public class SettingsActivity extends PreferenceActivity {

	Context mContext;
	AttributeSet attrs;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getFragmentManager().findFragmentByTag("settings") == null) {
			getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment(), "settings")
				.commit();
		}

//	AppsList MyAppsList = new AppsList(mContext, attrs);
//	MyAppsList = AppsList();
/*		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		ListPreference listPreferenceCategory = (ListPreference) findPreference("app_selector");
		if (listPreferenceCategory != null) {
			ArrayList<Category> categoryList = getCategories();
			CharSequence entries[] = new String[categoryList.size()];
			CharSequence entryValues[] = new String[categoryList.size()];
			int i = 0;
			for (Category category : categoryList) {
				entries[i] = category.getCategoryName();
				entryValues[i] = Integer.toString(i);
				i++;
			}
			listPreferenceCategory.setEntries(entries);
			listPreferenceCategory.setEntryValues(entryValues);
		} */

	}

}
