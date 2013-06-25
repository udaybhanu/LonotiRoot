package com.android.lonoti.activities;

import static com.android.lonoti.Config.SENDER_ID;
import com.crittercism.app.Crittercism;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.lonoti.Config;
import com.android.lonoti.R;
import com.android.lonoti.UserPreferences;
import com.android.lonoti.bom.UserData;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.dbhelper.DatabaseManager;
import com.android.lonoti.exception.NetworkException;
import com.android.lonoti.fragments.GetDetailsDialogFragment;
import com.android.lonoti.listeners.GetDetailsDialogListener;
import com.android.lonoti.network.LonotiAsyncServiceRequest;
import com.android.lonoti.network.ILonotiTaskListener;
import com.android.lonoti.network.LonotiServerManager;
import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  implements ILonotiTaskListener, GetDetailsDialogListener{
	
	private final String LOG_TAG = getClass().getSimpleName();
	
	private UserData userData;
	
	String regId;
	
	TextView progressText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UserPreferences.getInstance(true, this);
		setContentView(R.layout.activity_main);
		
		progressText = (TextView) findViewById(R.id.progressText);
		//First We see progress bar. Not even for a milli second if the response is received before that
		final ProgressBar progress = (ProgressBar) findViewById(com.android.lonoti.R.id.mainProgress);
		
		// init Crittercism
		initCrittercism();
		
		// initi DB
		initDatabase();
		
		checkUserAndPhoneNumber();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void doTask(String response) {
		//implement the if else appropriately once server code is integrated
		
		if(response == null){
			TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
	        String number = tm.getLine1Number();
			try {
				
				response = LonotiServerManager.callServer(Config.REGISTER_URL, "POST", 30000, true, "email=" + Config.DUMMY_USER + "&password=" + Config.DUMMY_PASSWORD + "&registration_id=" + regId + "&phone_number=" + number, true);
				
			} catch (NetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		JSONObject obj;
		try {
			obj = new JSONObject(response);
			String auth_token = obj.getString("auth_token");
			UserPreferences.getPreferences().put("authCode", auth_token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent intent = null;
		intent = new Intent(this, Home2Activity.class);
		/*if(response.equals("SUCCESS"))
		{
			//Got to Home screen directly
			
		}
		else
		{
			//Got to login screen
			intent = new Intent(this, LoginActivity.class);
		}*/
		this.startActivity(intent);
		this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		//We dont need this activity any more
		this.finish();
		
	}

	@Override
	public void doTask(Location location) {
		// TODO Auto-generated method stub
		
	}
	
//TODO: Implement destroyer
	
	private void initCrittercism(){
		/* Init crittercism */
		
		progressText.setText("Initializing Crittercism");
		
		JSONObject crittercismConfig = new JSONObject();
		try
		{
			crittercismConfig.put("includeVersionCode", true);
			crittercismConfig.put("shouldCollectLogcat", true);
		}
		catch (JSONException je){}

		Crittercism.init(getApplicationContext(), Config.CRITTERCISM_APP_ID, crittercismConfig);
		Crittercism.setUsername("user-mobile-No-notset");
		// instantiate metadata json object
		JSONObject metadata = new JSONObject();
		// add other user and app related metadata
		try {
			metadata.put("mobile_no", "notset");
			metadata.put("name", "not-set");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// send metadata to crittercism (asynchronously)
		Crittercism.setMetadata(metadata);
	}

	private void initDatabase(){
		
		progressText.setText("Initializing Database");
		
		DatabaseManager.init(this);
	}
	
	private void checkUserAndPhoneNumber(){
		
		progressText.setText("Checking User");
		
		UserData userData = DatabaseManager.getInstance().getUserData();
		
		if(userData == null){
			
			/*TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
	        String number = tm.getLine1Number();*/
	        
			FragmentManager fm = getSupportFragmentManager();
			GetDetailsDialogFragment gdf = new GetDetailsDialogFragment();
			gdf.show(fm, "fragment_get_details");
			
		}else{
			
			this.userData = userData;
			initGCM();
		}
		
	}

	@Override
	public void doListenerTask(Object data) {
		// TODO Auto-generated method stub
		UserData userData = (UserData) data;
		DatabaseManager.getInstance().createUserData(userData);
		
		this.userData = userData;
		initGCM();
		
	}
	
	
	private void initGCM(){
		
		progressText.setText("Initializing GCM");
		
		GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set
        GCMRegistrar.checkManifest(this);
        
        regId = GCMRegistrar.getRegistrationId(MainActivity.this);
        // Check if regid already presents
        if (regId.equals("")) {
        	// Registration is not present, register now with GCM
        	GCMRegistrar.register(MainActivity.this, SENDER_ID);
        	
        	doTask("SUCCESS");
        	
        }else{
        	
        	String AuthCode = UserPreferences.getPreferences().getString("authCode", Config.DEFAULT_AUTH_CODE);
            
            if(!AuthCode.equals(Config.DEFAULT_AUTH_CODE)){
            	
            	doTask("SUCCESS");
            	
            }else{
            	
            	LonotiAsyncServiceRequest asyncRequet = new LonotiAsyncServiceRequest(this);
            	
            	asyncRequet.execute(Config.LOGIN_URI, "POST", 30000, true, "email=" + this.userData.getEmail() +  "&password=" + Config.DUMMY_PASSWORD);
            	
            }
        }
	}
}
