package com.android.lonoti;

import java.util.ArrayList;

import com.android.lonoti.activities.LonotiEventCreate;
import com.android.lonoti.adapter.EventListAdapter;
import com.android.lonoti.adapter.data.EventRowData;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class EventActivity extends ListFragment {

	//EventListAdapter adapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View myFragmentView = inflater.inflate(R.layout.activity_event, container, false);
		
		return myFragmentView;
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		setListAdapter(new EventListAdapter(getActivity(), R.layout.event_row, getEventRowList()));
		
	    getListView().setTextFilterEnabled(true);
	    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		//View v = (View) menu.findItem(R.id.add).getActionView();
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.envent, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId() == R.id.newEvent){
			
			Intent intent = new Intent(getActivity(), LonotiEventCreate.class);
			startActivity(intent);
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		//Log.
		super.onListItemClick(l, v, position, id);
	}
	
	private ArrayList<EventRowData> getEventRowList(){
		ArrayList<EventRowData> eventRowList = new ArrayList<EventRowData>();
		EventRowData row1 = new EventRowData("One", false);
		EventRowData row2 = new EventRowData("Two", false);
		EventRowData row3 = new EventRowData("Three", false);
		EventRowData row4 = new EventRowData("Four", false);
		EventRowData row5 = new EventRowData("Five", false);
		EventRowData row6 = new EventRowData("Six", false);
		eventRowList.add(row1);
		eventRowList.add(row2);
		eventRowList.add(row3);
		eventRowList.add(row4);
		eventRowList.add(row5);
		eventRowList.add(row6);
		return eventRowList;
	}

}
