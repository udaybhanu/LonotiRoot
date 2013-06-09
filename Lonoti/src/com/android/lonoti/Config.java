package com.android.lonoti;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class Config {
	
	public static final String SERVER_URL = "";
	
	// Google project id
    public static final String SENDER_ID = "696066559861";
    
    public final static String LOGIN_URI = "https://lonoti.herokuapp.com/api/users/sign_in";
	public final static String SERVER_URL_AUTOCOMPLETE = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
	public final static String SERVER_URL_LOCATION_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json";
	public final static String REGISTER_URL = "https://lonoti.herokuapp.com/api/users";
	public final static String EVENT_NEW_URL = "https://lonoti.herokuapp.com/api/events";
	
	public final static String DUMMY_USER = "user@test.com";
	public final static String DUMMY_PASSWORD = "password";
	
	public final static String[] weekdays = new DateFormatSymbols().getWeekdays();
	
	public final static String[] values = partArray(weekdays);/*new String[] {
            "None",
            "Every Day",
            "Every Week",
            "Every 2 Weeks",
            "Every Month",
            "Every Year",
            "Custom",
        };*/
	
	public final static String LOCATION_ICON_STRING = "%LOC%";
	
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public final static SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private static String[] partArray(String[] weekdays){
		String[] values = new String[7];
		System.arraycopy(weekdays, 1, values, 0, 7);
		return values;
	}
	
}
