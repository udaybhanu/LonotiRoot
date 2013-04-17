package com.android.lonoti.location;

import com.android.lonoti.exception.LocationException;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class LonotiLocationManager {

	private static LonotiLocationManager lonotiLocationManager;
	private LocationManager locationManager; 
	private LocationListener listener;
	private Location lastLocation;
	
	private LonotiLocationManager() throws LocationException{
		// TODO Auto-generated constructor stub
		throw new LocationException("Context Undefined");
	}
	
	private LonotiLocationManager(Context context){
		// TODO Auto-generated constructor stub
		locationManager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		listener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String s, int i, Bundle bundle) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String s) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String s) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if(location != null){
					lastLocation = location;
				}
				locationManager.removeUpdates(listener);
			}
		};
	}
	
	public static LonotiLocationManager getInstance(Context context){
		if(lonotiLocationManager == null)
			return new LonotiLocationManager(context);
		 else 
			return lonotiLocationManager;
	}
	
	public Location requestLocation() throws LocationException{
		return this.requestLocation(null);
	}
	
	public Location requestLocation(String criteria) throws LocationException{
		
		String providerName = getBestProvider();
		if(providerName == null){
			throw new LocationException("No providers available");
		}
		
		locationManager.requestLocationUpdates(providerName, 0, 0, listener);
		
		return lastLocation;
	}
	
	public Intent getFirableIntent(String provider){
		Boolean providerEnabled = locationManager.isProviderEnabled(provider);
		if(!providerEnabled){
			return new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		}
		return null;
	}
	
	private String getBestProvider(){
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		return provider;
	}
	
}
