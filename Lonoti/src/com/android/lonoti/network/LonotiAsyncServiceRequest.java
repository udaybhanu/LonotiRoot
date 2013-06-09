package com.android.lonoti.network;

import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.exception.NetworkException;
import com.android.lonoti.location.LonotiLocationPlaces;

import android.os.AsyncTask;
import android.util.Log;

public class LonotiAsyncServiceRequest extends AsyncTask<Object, Integer, Long>{

	ILonotiTaskListener listener;
	
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 * 
	 * In generic method to make a server request. Inputs are 
	 * String URL, String requestType (GET or POST),int timeout, boolean isLonotiRequest String data, TaskListener listener
	 * 
	 */
	
	public LonotiAsyncServiceRequest(ILonotiTaskListener listener) {
		// TODO Auto-generated constructor stub
		this.listener = listener;
	}
	
	@Override
	protected Long doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		
		String response = null;
		
		if(arg0.length == 5) {
			
			try {
				response = LonotiServerManager.callServer((String)arg0[0], (String)arg0[1], (Integer) arg0[2], (Boolean) arg0[3], (String) arg0[4]);
				listener.doTask(response);
			} catch (NetworkException e) {
				// TODO Auto-generated catch block
				Log.e("NetworkException", e.getMessage());
			}
		}
		
		if(arg0.length == 2){
			
			if(arg0[0].equals("REFERENCE_SEARCH")){
				
				try {
					Location location = LonotiLocationPlaces.getLocation(arg0[1].toString());
					listener.doTask(location);
				} catch (NetworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(arg0[0].equals("LOCATION_SEARCH")){
				
				try {
					response = LonotiLocationPlaces.getLocationDescription((Location) arg0[1]);
					listener.doTask(response);
				} catch (NetworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

}
