package com.android.lonoti;

import android.app.Activity;
import android.content.Intent;

public class LonotiThread implements Runnable{

	private Activity activity;
	
	public LonotiThread(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent intent = new Intent(activity, HomeActivity.class);
		activity.startActivity(intent);
		activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}

}
