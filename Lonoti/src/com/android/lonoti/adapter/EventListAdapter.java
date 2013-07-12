package com.android.lonoti.adapter;

import java.util.ArrayList;

import com.android.lonoti.R;
import com.android.lonoti.activities.EventActivity;
import com.android.lonoti.activities.LonotiEventCreate;
import com.android.lonoti.adapter.data.EventRowData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filter.FilterListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EventListAdapter extends ArrayAdapter<EventRowData>{
	
	private ArrayList<EventRowData> eventList;
	Activity activity;
	
	private class ViewHolder{
		TextView textView;
		CheckBox checkBox;
		ToggleButton toggleButton1;
		LinearLayout itemLayout;
		TextView textDescription;
	}

	public EventListAdapter(Activity activity, int textViewResourceId, 
		    ArrayList<EventRowData> countryList) {
		super(activity, textViewResourceId, countryList);
		this.activity = activity;
		this.eventList = new ArrayList<EventRowData>();
		this.eventList.addAll(countryList);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		   Log.v("ConvertView", String.valueOf(position));
		 
		   if (convertView == null) {
		   LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.event_row, null);
		 
		   holder = new ViewHolder();
		   holder.textView = (TextView) convertView.findViewById(R.id.event_item_text);
		   holder.checkBox = (CheckBox) convertView.findViewById(R.id.event_item_select);
		   holder.toggleButton1 = (ToggleButton) convertView.findViewById(R.id.toggleButton1);
		   holder.itemLayout = (LinearLayout) convertView.findViewById(R.id.event_item_layout_clickable);
		   holder.textDescription = (TextView) convertView.findViewById(R.id.event_item_description);
		   holder.checkBox.setOnClickListener( new View.OnClickListener() {  
		     public void onClick(View v) {  
		      CheckBox cb = (CheckBox) v ;  
		      EventRowData eventRow = (EventRowData) cb.getTag();  
		      Toast.makeText(activity.getApplicationContext(),
		       "Clicked on Checkbox: " + cb.getText() +
		       " is " + cb.isChecked(), 
		       Toast.LENGTH_LONG).show();
		      eventRow.setIsChecked(cb.isChecked());
		     }
		    });
		   	
		   	
		   	holder.itemLayout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					LinearLayout tv = (LinearLayout) view;
					EventRowData eventRow = (EventRowData) tv.getTag();
					Intent intent = new Intent(activity, LonotiEventCreate.class);
					intent.putExtra("event_id", eventRow.getEvent().getId());
					activity.startActivity(intent);
					activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				}
			});
		   
		   } 
		   else {
		    holder = (ViewHolder) convertView.getTag();
		   }
		   
		   EventRowData eventRow = eventList.get(position);
		   holder.textView.setText(eventRow.getText());
		   holder.itemLayout.setTag(eventRow);
		   //holder.checkBox.setText(eventRow.getText());
		   //holder.checkBox.setChecked(eventRow.getIsChecked());
		   //holder.checkBox.setTag(eventRow);
		   holder.toggleButton1.setChecked(eventRow.getEvent().isEnabled());
		   holder.textDescription.setText(eventRow.getEvent().getDescription());
		   convertView.setTag(holder);
		   return convertView;
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter(){

			@Override
			protected FilterResults performFiltering(CharSequence charsequence) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void publishResults(CharSequence charsequence,
					FilterResults filterresults) {
				// TODO Auto-generated method stub
				
			}};
	}
	
	
	
}
