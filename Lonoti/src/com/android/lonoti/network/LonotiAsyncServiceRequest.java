package com.android.lonoti.network;

import com.android.lonoti.exception.NetworkException;

import android.os.AsyncTask;
import android.util.Log;

public class LonotiAsyncServiceRequest extends AsyncTask<Object, Integer, Long>{

	LonotiTaskListener listener;
	
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 * 
	 * In generic method to make a server request. Inputs are 
	 * String URL, String requestType (GET or POST),int timeout, boolean isLonotiRequest String data, TaskListener listener
	 * 
	 */
	
	public LonotiAsyncServiceRequest(LonotiTaskListener listener) {
		// TODO Auto-generated constructor stub
		this.listener = listener;
	}
	
	@Override
	protected Long doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.length != 6) return null;
		
		String response = null;
		
		try {
			response = LonotiServerManager.callServer((String)arg0[0], (String)arg0[1], (Integer) arg0[2], (Boolean) arg0[3], (String) arg0[4]);
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			Log.e("NetworkException", e.getMessage());
		}
		
		listener.doTask(response);
		
		return null;
	}

}
