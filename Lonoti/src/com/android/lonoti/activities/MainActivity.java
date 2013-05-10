package com.android.lonoti.activities;

import static com.android.lonoti.Config.SENDER_ID;

import java.util.List;

import com.android.lonoti.R;
import com.android.lonoti.UserPreferences;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.dbhelper.DatabaseManager;
import com.android.lonoti.network.LonotiAsyncServiceRequest;
import com.android.lonoti.network.LonotiTaskListener;
import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  implements LonotiTaskListener{
	
	private final String LOG_TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DatabaseManager.init(this);
		UserPreferences.getInstance(true, this);
		setContentView(R.layout.activity_main);
		
		//First We see progress bar. Not even for a milli second if the response is received before that
		final ProgressBar progress = (ProgressBar) findViewById(com.android.lonoti.R.id.mainProgress);
		
		//Now see if the the current Authcode is valid - if yes Dont show login screen but show Home Screen
		
		//Get existing auth code
		String AuthCode = UserPreferences.getPreferences().getString("authCode", "AuthcodeNotPresent");
		//Check Auth status by sending a server request with current Authcode and user details
		//That will call doTask() which will start the appropriate Activity:Home or login 
		
		//form and fire request
		/*LonotiAsyncServiceRequest checkLogin = new LonotiAsyncServiceRequest(this);
		checkLogin.execute(String serverURL, String httpMethod, int timeout, boolean isLonotiRequest, String payload);
		*/
		//For now I will call do Task here Once server code is integrated, remove following line.
		doTask("SUCCESS");
		
		/* TODO: Move the GCM registritaion process to appropriate location
		 * // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set
        GCMRegistrar.checkManifest(this);
        
        String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
        // Check if regid already presents
        if (regId.equals("")) {
        	// Registration is not present, register now with GCM
        	GCMRegistrar.register(MainActivity.this, SENDER_ID);
        } 
        
		final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
            	
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });
        final Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
            	TextView reEnter = (TextView) findViewById(R.id.textReEnterPassword);
            	if(reEnter.getVisibility() == View.GONE){
            		reEnter.setVisibility(View.VISIBLE);
            	}else{
            		//here we would actually call the login server urls 
            		//and call the below Activity once we get a successful response
            		//Some where in between we will send the regID we acquire from the
            		//GCM to indicate that this is the users Android device.
            		
            		String regId = GCMRegistrar.getRegistrationId(MainActivity.this);
                	// Check if regid already presents
                    if (regId.equals("")) {
                        // Registration is not present, register now with GCM
                        GCMRegistrar.register(MainActivity.this, SENDER_ID);
                    } else {
                    	//TODO: uncomment below line after testing
                    	//GCMRegistrar.unregister(MainActivity.this);
                        // Device is already registered on GCM so update it to server while registering
                        if (GCMRegistrar.isRegisteredOnServer(MainActivity.this)) {
                            // This pnly means that the regID is set on server also. so don't relay on this
                            Toast.makeText(getApplicationContext(), "Already registered with GCM with regid" + regId, Toast.LENGTH_LONG).show();
                        } else {
                            //TODO: NOt logged In do some thing?                
                        }
                    }
            		
            		Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            		finish();
                    startActivity(intent);
            	}
            }
        });
*/	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void doTask(String response) {
		//implement the if else appropriately once server code is integrated
		Intent intent = null;
		if(response.equals("SUCCESS"))
		{
			//Got to Home screen directly
			intent = new Intent(this, HomeActivity.class);
		}
		else
		{
			//Got to login screen
			intent = new Intent(this, LoginActivity.class);
		}
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

}
