package com.android.lonoti;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPreferences {
	
	private static UserPreferences userPreference;

	private Context context;

	private static String SHARED_PREFS_NAME = "generalPreferences";
	
	private UserPreferences(Context context) {
		this.context = context;
	}
	
	public static synchronized UserPreferences getInstance(boolean createNew, Context context) {
		if(userPreference == null || createNew)
			userPreference = new UserPreferences(context);
		return userPreference;
	}
	
	public static UserPreferences getPreferences() {
		return userPreference;
	}
	
	public void put(String key, String value) {
		SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getString(String key, String defValue) {
		SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
		String prefVal = sharedPreferences.getString(key, defValue);
		return prefVal;
	}
	
	public String getString(String key) {
		return this.getString(key, null);
	}

}
