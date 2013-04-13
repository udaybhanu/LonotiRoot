package com.android.lonoti;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class EventActivity extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View myFragmentView = inflater.inflate(R.layout.activity_event, container, false);
		
		return myFragmentView;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.envent, menu);
		//View v = (View) menu.findItem(R.id.add).getActionView();
		//super.onCreateOptionsMenu(menu, inflater);
	}

}
