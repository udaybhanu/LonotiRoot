package com.android.lonoti.activities;


import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.android.lonoti.R;
import com.android.lonoti.activies.map.MapSelectActivity;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.bom.payload.LonotiEvent;
import com.android.lonoti.dbhelper.DatabaseManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Layout;
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
import android.widget.ToggleButton;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Home2Activity extends FragmentActivity {
	private static final int HOME_VIEW_ID = 0101010;
	private static final int TITLE_VIEW_ID = 0101011;
	private static final int BUTTONS_VIEW_ID = 0101012;
	private static final int EVENTS_VIEW_ID = 0101013;
	private static final int NOTIFICATIONS_VIEW_ID = 0101014;
	private static final int MENU_VIEW_ID = 0101016;
	private static int boxCount = 0;
	LinearLayout parent = null;
	FragmentTransaction ft = null;
	ListFragment events = new EventActivity();
	//For Now add Events till Notification fragment is in good shape
	ListFragment notifications = new EventActivity();
	Fragment menu = new HomeMenuFragment();
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
		
        ImageButton mapBtn = new ImageButton(this);
        mapBtn.setImageResource(android.R.drawable.ic_menu_mapmode);
        mapBtn.setBackgroundColor(0xFF406666);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(context, MapSelectActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        mapBtn.setLayoutParams(lp);
        buttonsLayout.addView(mapBtn);
        
        ImageButton timeBtn = new ImageButton(this);
        timeBtn.setImageResource(android.R.drawable.ic_menu_agenda);
        timeBtn.setBackgroundColor(0xFF406666);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(context, CalendarActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    /*Toast.makeText(context, "OTHER Button clicked this should start new activit with slide motion", 
                        Toast.LENGTH_LONG).show();*/
            }
        });
        timeBtn.setLayoutParams(lp);
        buttonsLayout.addView(timeBtn);
        
        ImageButton createEventButton = new ImageButton(this);
        createEventButton.setImageResource(android.R.drawable.ic_menu_add);
        createEventButton.setBackgroundColor(0xFF406666);
        createEventButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, LonotiEventCreate.class);
				startActivity(intent);
			}
		});
        buttonsLayout.addView(createEventButton);
        final ToggleButton toggleEnabling = new ToggleButton(this);
        toggleEnabling.setHint("Enable of disable Your location tracking");
        toggleEnabling.setText("Active");
        toggleEnabling.setBackgroundColor(0xFF406060);
        toggleEnabling.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(toggleEnabling.isChecked())
				{
					toggleEnabling.setText("Disabled");
					toggleEnabling.setBackgroundColor(0xFF666060);
				}
				else
				{
					toggleEnabling.setText("Active");
					toggleEnabling.setBackgroundColor(0xFF406060);
				}
				// TODO Auto-generated method stub
			}
		});
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.FILL_PARENT);
        toggleEnabling.setLayoutParams(lp2);
        buttonsLayout.addView(toggleEnabling);
        
        baap.addView(buttonsLayout);
	}
	
	private LonotiEvent getActiveLocationEvent()
	{
		//TO Do write loging to indentify the right event
		if( DatabaseManager.getInstance().getAllLonotiEvents().size() == 0)
		{
			Location forum = new Location("0", "0", "Origin");
			LonotiEvent event = new LonotiEvent("No Locations", "Remind people that I am at No location", forum);
			return event;
		}
		else
		{
			return DatabaseManager.getInstance().getAllLonotiEvents().get(0);
		}
	}
	
	private LonotiEvent getActiveTimeEvent()
	{
			if( DatabaseManager.getInstance().getAllLonotiEvents().size() == 0)
			{
				Location forum = new Location("0", "0", "Origin");
				LonotiEvent event = new LonotiEvent("No Time Event", "Vinot and Pappa are listening to this time eevent", forum);
				return event;
			}
			else
			{
				return DatabaseManager.getInstance().getAllLonotiEvents().get(0);
			}
	}
	
	protected void addBottomButtons(LinearLayout baap)
	{
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT,0.5f);
		LinearLayout buttonsLayout = new LinearLayout(this);
		
        Button mapBtn = new Button(this);
        String name = getActiveLocationEvent().getName();
        mapBtn.setText(name.substring(0, name.length() > 8 ? 8 : name.length()));
        // Setting the Buttons OnClickListener
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context, getActiveLocationEvent().getDescription(), 
                        Toast.LENGTH_LONG).show();
            }
        });
        mapBtn.setLayoutParams(lp);
        buttonsLayout.addView(mapBtn);
        
        Button otherBtn = new Button(this);
        String name2 = getActiveTimeEvent().getName();
        otherBtn.setText(name2.substring(0, name2.length() > 8 ? 8 : name2.length()));
        // Setting the Buttons OnClickListener
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(context, getActiveTimeEvent().getDescription(), 
                        Toast.LENGTH_LONG).show();
            }
        });
        otherBtn.setLayoutParams(lp);
        buttonsLayout.addView(otherBtn);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
	            LinearLayout.LayoutParams.MATCH_PARENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT,0.1f);
        //otherBtn.setLeft(200);
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

	protected void addMenu()
	{
		if(parent != null && ft !=null)
		{
			LinearLayout menuLayout = new LinearLayout(this.getApplicationContext());
			menuLayout.setBackgroundColor(0xffe2fffe);
			menuLayout.setOrientation(LinearLayout.VERTICAL);
			menuLayout.setId(MENU_VIEW_ID);
			parent.addView(menuLayout);
			
            ft.replace(MENU_VIEW_ID, menu);
            
            setLayoutParams();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(DatabaseManager.getInstance().getUserData().getEmail());
		context = this.getApplicationContext();
		//Wrap parent in a scroll View
		LinearLayout baap = new LinearLayout(this);
		baap.setOrientation(LinearLayout.VERTICAL);
		baap.setWeightSum(1.0f);
		/*int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView titleView = (TextView)findViewById(titleId);*/
		addTopButtons(baap);
		
		ScrollView beta = new ScrollView(this);
		
		parent = new LinearLayout(this);
		parent.setBackgroundColor(0xffffff00);
		parent.setOrientation(LinearLayout.VERTICAL);
		parent.setWeightSum(1.0f);
		parent.setId(HOME_VIEW_ID);
		parent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,0.9f));
		
		baap.addView(parent);
		/*beta.addView(parent);
		beta.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		baap.addView(baccha);*/
		addBottomButtons(baap);
		setContentView(baap, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		ft = getSupportFragmentManager().beginTransaction();
		addMenu();
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
		//events.on

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
