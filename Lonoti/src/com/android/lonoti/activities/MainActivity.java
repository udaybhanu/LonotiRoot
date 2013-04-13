package com.android.lonoti.activities;

import static com.android.lonoti.Config.SENDER_ID;

import java.util.List;

import com.android.lonoti.HomeActivity;
import com.android.lonoti.R;
import com.android.lonoti.dbhelper.DatabaseHelper;
import com.android.lonoti.dbhelper.SimpleData;
import com.google.android.gcm.GCMRegistrar;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	private final String LOG_TAG = getClass().getSimpleName();
	private static DatabaseHelper databaseHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDBHelper();
		//See following commented function function to know how to retrieve data from a table
		//One can access This function from any where to get data from DB
		//getDBData();
		setContentView(R.layout.activity_main);
		/*// Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);
        // Make sure the manifest was properly set
        GCMRegistrar.checkManifest(this);
        
		final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
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
                        // This only means that the regID is set on server also. so don't relay on this
                        Toast.makeText(getApplicationContext(), "Already registered with GCM with regid" + regId, Toast.LENGTH_LONG).show();
                    } else {
                        //TODO: NOt logged In do some thing?                
                    }
                }
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
	
	private void setDBHelper()
	{
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
	}
	public static DatabaseHelper getDBHelper() {
		return databaseHelper;
	}
	
	private void getDBData(){
		try {
			Dao<SimpleData, Integer> simpleDao = null;
			try {
				simpleDao = MainActivity.getDBHelper().getSimpleDataDao();
			} catch (java.sql.SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(simpleDao == null)
			{
				return;
			}
			
			// query for all of the data objects in the database
			List<SimpleData> list = null;
			try {
				list = simpleDao.queryForAll();
			} catch (java.sql.SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list == null)
			{
				return;
			}
			for (SimpleData simple : list)
			{
				Log.i(LOG_TAG, "entry from table simple(" + simple + ")");
			}
		}
		catch (SQLException e) {
			Log.e(LOG_TAG, "Database exception", e);
			return;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
//TODO: Implement destroyer

}
