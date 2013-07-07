package com.android.lonoti;
 
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
 
import com.android.lonoti.Config;
import com.android.lonoti.R;
import com.android.lonoti.UserPreferences;
import com.android.lonoti.activities.MainActivity;
import com.android.lonoti.bom.UserData;
import com.android.lonoti.dbhelper.DatabaseManager;
import com.android.lonoti.exception.NetworkException;
import com.android.lonoti.network.LonotiServerManager;
import com.android.lonoti.gcm.*;
import com.google.android.gcm.GCMBaseIntentService;
 
import static com.android.lonoti.Config.SENDER_ID;
 
public class GCMIntentService extends GCMBaseIntentService {
 
    private static final String TAG = "GCMIntentService";
 
    public GCMIntentService() {
        super(SENDER_ID);
    }
 
    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        //TODO Decision on sending registration id to server based on auth token
        
        UserData userData = DatabaseManager.getInstance().getUserData();
        
        String serverUrl = Config.REGISTER_URL;
        
        try {
			String response = LonotiServerManager.callServer(serverUrl, "POST", 30000, true, "email=" + userData.getEmail() + "&password=" + Config.DUMMY_PASSWORD + "&registration_id=" + registrationId + "&phone_number=" + userData.getPhonenumber(), true);
			
			JSONObject obj = new JSONObject(response);
			
			String auth_token = ((JSONObject) obj).getString("auth_token");
			
			UserPreferences.getPreferences().put("authCode", auth_token);
			
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			Log.e("NetworkException", e.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSONException", e.getMessage());
		}
        
    }
 
    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        //TODO Notify server that the Device can no more be reached through GCM
    }
 
    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
    	String collapse_key = intent.getStringExtra("collapse_key");
    	//if collapse key exists it is a one-time message
        GcmCollapseKey key = GcmCollapseKey.getKey(collapse_key);
        if(key != null)
        {
        switch (key)
        {
        case GET_LOCATION:
        	break;
        case SYNC_DB:
        	break;
        	default:
        		//no valid collapse key.
        		//Do internal implementation
        	break;	
        }
        }
 
        // notifies user
        //TODO : Also do other stuff based on the message
        Log.e(TAG,intent.getExtras().toString());
        generateNotification(context, ""+ intent.getExtras().toString());
    }
 
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        //TODO: What to do if a message is lost 
    }
 
    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }
 
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }
 
    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
 
        String title = context.getString(R.string.app_name);
 
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
 
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
 
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      
 
    }
 
}