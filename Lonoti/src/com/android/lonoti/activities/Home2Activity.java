package com.android.lonoti.activities;


import com.android.lonoti.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("NewApi")
public class Home2Activity extends Activity {
	private static final int HOME_VIEW_ID = 0101010;
	private static final int TITLE_VIEW_ID = 0101011;
	private static final int BUTTONS_VIEW_ID = 0101012;
	private static final int EVENTS_VIEW_ID = 0101013;
	private static final int NOTIFICATIONS_VIEW_ID = 0101014;
	private static int boxCount = 0;
	LinearLayout parent = null;
	FragmentTransaction ft = null;
	ListFragment events = new EventActivity();
	//For Now add Events till Notification fragment is in good shape
	ListFragment notifications = new EventActivity();
	Context context;
	
	protected void setLayoutParams()
	{
		float weight = 1.0f/(float)boxCount;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, weight);
		View eventLayout = findViewById(EVENTS_VIEW_ID);
		if(eventLayout != null)
			eventLayout.setLayoutParams(params);
		View notificationsLayout = findViewById(NOTIFICATIONS_VIEW_ID);
		if(notificationsLayout != null)
			notificationsLayout.setLayoutParams(params);
	}
	protected void addTopButtons(LinearLayout baap)
	{
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.WRAP_CONTENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout buttonsLayout = new LinearLayout(this);
		
        Button mapBtn = new Button(this);
        mapBtn.setText("MAP");
        // Setting the Buttons OnClickListener
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context, "MAP Button clicked this should start new activit with slide motion", 
                        Toast.LENGTH_LONG).show();
            }
        });
        mapBtn.setLayoutParams(lp);
        buttonsLayout.addView(mapBtn);
        
        Button otherBtn = new Button(this);
        otherBtn.setText("OTHER");
        // Setting the Buttons OnClickListener
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context, "OTHER Button clicked this should start new activit with slide motion", 
                        Toast.LENGTH_LONG).show();
            }
        });
        otherBtn.setLayoutParams(lp);
        buttonsLayout.addView(otherBtn);
        otherBtn.setLeft(200);
        
        ImageButton createEventButton = new ImageButton(this);
        //Button createEventButton = new Button(this);
        createEventButton.setImageResource(android.R.drawable.ic_menu_add);
        //createEventButton.setBackgroundResource(android.R.drawable.ic_menu_add);
        createEventButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, LonotiEventCreate.class);
				startActivity(intent);
			}
		});
        buttonsLayout.addView(createEventButton);
        baap.addView(buttonsLayout);
	}
	
	protected void addBottomButtons(LinearLayout baap)
	{
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.WRAP_CONTENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout buttonsLayout = new LinearLayout(this);
		
        Button mapBtn = new Button(this);
        mapBtn.setText("BottomButton1");
        // Setting the Buttons OnClickListener
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context, "BottomButton1 clicked this should start new activit with slide motion", 
                        Toast.LENGTH_LONG).show();
            }
        });
        mapBtn.setLayoutParams(lp);
        buttonsLayout.addView(mapBtn);
        
        Button otherBtn = new Button(this);
        otherBtn.setText("BottomButton2");
        // Setting the Buttons OnClickListener
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context, "BottomButton2 clicked this should start new activit with slide motion", 
                        Toast.LENGTH_LONG).show();
            }
        });
        otherBtn.setLayoutParams(lp);
        buttonsLayout.addView(otherBtn);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT,0.1f);
        otherBtn.setLeft(200);
        baap.addView(buttonsLayout);
	}
	protected void addEvents()
	{
		if(parent != null && ft !=null)
		{
			LinearLayout eventLayout = new LinearLayout(this.getApplicationContext());
			eventLayout.setBackgroundColor(0xffe2fffe);
			eventLayout.setOrientation(LinearLayout.VERTICAL);
			eventLayout.setId(EVENTS_VIEW_ID);
			boxCount++;
			parent.addView(eventLayout);
			
			   // Creating a new TextView For Heading
	        final TextView tv = new TextView(this);
	        tv.setBackgroundColor(0xFF53868b);
	        tv.setTextColor(0xFF000000);
	        tv.setTypeface(null, Typeface.BOLD);
	        tv.setText("Events");
	        tv.setGravity(Gravity.CENTER_HORIZONTAL);
	         
	        // Defining the layout parameters of the TextView
	        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT);
	        // lp.width = lp.width + 10;
	        // Setting the parameters on the TextView
	        tv.setLayoutParams(lp);
	         
	        // Adding the TextView to the LinearLayout as a child
	        eventLayout.addView(tv);
			
			events.setMenuVisibility(false);
            ft.replace(EVENTS_VIEW_ID, events);
            
            setLayoutParams();
		}
	}
	protected void removeEvents()
	{
		if(parent != null && ft !=null)
		{
			ft.remove(events);
			parent.removeView(findViewById(EVENTS_VIEW_ID));
			boxCount--;
		}
	}
	
	protected void addNotifications()
	{
		if(parent != null && ft !=null)
		{
			LinearLayout notificationsLayout = new LinearLayout(this.getApplicationContext());
			notificationsLayout.setBackgroundColor(0xffe2fffe);
			notificationsLayout.setOrientation(1);
			notificationsLayout.setId(NOTIFICATIONS_VIEW_ID);
			boxCount++;
			parent.addView(notificationsLayout);
			
			   // Creating a new TextView For Heading
	        final TextView tv = new TextView(this);
	        tv.setBackgroundColor(0xFF53868b);
	        tv.setTextColor(0xFF000000);
	        tv.setTypeface(null, Typeface.BOLD);
	        tv.setText("Notifications");
	        tv.setGravity(Gravity.CENTER_HORIZONTAL);
	         
	        // Defining the layout parameters of the TextView
	        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT);
	        // lp.width = lp.width + 10;
	        // Setting the parameters on the TextView
	        tv.setLayoutParams(lp);
	         
	        // Adding the TextView to the LinearLayout as a child
	        notificationsLayout.addView(tv);
			notifications.setMenuVisibility(false);
			ft.replace(NOTIFICATIONS_VIEW_ID, notifications);
            
            setLayoutParams();
		}
	}
	
	protected void removeNotifications()
	{
		if(parent != null && ft !=null)
		{
			ft.remove(notifications);
			parent.removeView(findViewById(NOTIFICATIONS_VIEW_ID));
			boxCount--;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("UserName");
		context = this.getApplicationContext();
		//Wrap parent in a scroll View
		LinearLayout baap = new LinearLayout(this);
		baap.setOrientation(LinearLayout.VERTICAL);
		baap.setWeightSum(1.0f);
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView titleView = (TextView)findViewById(titleId);
		addTopButtons(baap);
		
		ScrollView baccha = new ScrollView(this);
		
		parent = new LinearLayout(this);
		parent.setBackgroundColor(0xffffff00);
		parent.setOrientation(LinearLayout.VERTICAL);
		parent.setWeightSum(1.0f);
		parent.setId(HOME_VIEW_ID);
		parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,0.9f));
		
		baap.addView(parent);
		/*baccha.addView(parent);
		baccha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		baap.addView(baccha);*/
		addBottomButtons(baap);
		setContentView(baap, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		ft = getFragmentManager().beginTransaction();
		
		addEvents();
		addNotifications();
		ft.commit();
		
		
		final View events = findViewById(EVENTS_VIEW_ID);
		//final View notifications = findViewById(R.id.notificationlist);

		// Set up the user interaction to manually show or hide the system UI.
		events.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				Log.e("EVENTS","Some one touched me");
				/*if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}*/
			}
		});

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		Log.e("BETTER","POST Create handling");
		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}
}
