package com.android.lonoti.location;

import com.android.lonoti.exception.LocationException;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationManagerTest {

	public static void main(String args[]) throws LocationException{
		
		final LonotiLocationManager manager = LonotiLocationManager.getInstance(new Activity());
		
		manager.requestLocation(null, new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				manager.unRegisterListener(this);
				
			}
		});
		
	}
	
}
