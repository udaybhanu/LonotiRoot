package com.android.lonoti;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;

@SuppressLint("NewApi")
public class HomeActivity extends FragmentActivity {

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_home);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			String label1 = "Events";
			Tab tab = actionBar.newTab();
			tab.setText(label1);
			TabListener<EventActivity> tl = new TabListener<EventActivity>(this,
					label1, EventActivity.class);
			tab.setTabListener(tl);
			
			actionBar.addTab(tab);
			
			String label2 = "Notifications";
			tab = actionBar.newTab();
			tab.setText(label2);
			TabListener<NotificationsActivity> tl2 = new TabListener<NotificationsActivity>(this,
					label2, NotificationsActivity.class);
			tab.setTabListener(tl2);
			actionBar.addTab(tab);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	private class TabListener<T extends Fragment> implements
	ActionBar.TabListener {
private Fragment mFragment;
private final Activity mActivity;
private final String mTag;
private final Class<T> mClass;

/**
 * Constructor used each time a new tab is created.
 * 
 * @param activity
 *            The host Activity, used to instantiate the fragment
 * @param tag
 *            The identifier tag for the fragment
 * @param clz
 *            The fragment's Class, used to instantiate the fragment
 */
public TabListener(Activity activity, String tag, Class<T> clz) {
	mActivity = activity;
	mTag = tag;
	mClass = clz;
}

public void onTabSelected(Tab tab, FragmentTransaction ft) {
	// Check if the fragment is already initialized
	if (mFragment == null) {
		// If not, instantiate and add it to the activity
		mFragment = Fragment.instantiate(mActivity, mClass.getName());
		ft.add(android.R.id.content, mFragment, mTag);
	} else {
		// If it exists, simply attach it in order to show it
		ft.attach(mFragment);
	}
}

public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	if (mFragment != null) {
		// Detach the fragment, because another one is being attached
		ft.detach(mFragment);
	}
}

public void onTabReselected(Tab tab, FragmentTransaction ft) {
	// User selected the already selected tab. Usually do nothing.
}
}

}
