package com.android.lonoti;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public final class Config {
	
	public static final String SERVER_URL = "";
	
	// Google project id
    public static final String SENDER_ID = "696066559861";
    
    public final static String LOGIN_URI = "https://lonoti.herokuapp.com/api/users/sign_in";
	public final static String SERVER_URL_AUTOCOMPLETE = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
	public final static String SERVER_URL_LOCATION_DETAILS = "https://maps.googleapis.com/maps/api/place/details/json";

	public final static String[] weekdays = new DateFormatSymbols().getWeekdays();
	
	public final static String[] values = new String[] {
            "None",
            "Every Day",
            "Every Week",
            "Every 2 Weeks",
            "Every Month",
            "Every Year",
            "Custom",
        };
	
	public final static String LOCATION_ICON_STRING = "%LOC%";
	
}
