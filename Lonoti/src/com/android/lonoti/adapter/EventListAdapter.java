package com.android.lonoti.adapter;

import java.util.ArrayList;

import com.android.lonoti.R;
import com.android.lonoti.adapter.data.EventRowData;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filter.FilterListener;
import android.widget.TextView;
import android.widget.Toast;

public class EventListAdapter extends ArrayAdapter<EventRowData>{
	
	private ArrayList<EventRowData> eventList;
	Context context;
	
	private class ViewHolder{
		TextView textView;
		CheckBox checkBox;
	}

	public EventListAdapter(Context context, int textViewResourceId, 
		    ArrayList<EventRowData> countryList) {
		super(context, textViewResourceId, countryList);
		this.context = context;
		this.eventList = new ArrayList<EventRowData>();
		this.eventList.addAll(countryList);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		   Log.v("ConvertView", String.valueOf(position));
		 
		   if (convertView == null) {
		   LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.event_row, null);
		 
		   holder = new ViewHolder();
		   holder.textView = (TextView) convertView.findViewById(R.id.event_item_text);
		   holder.checkBox = (CheckBox) convertView.findViewById(R.id.event_item_select);
		 
		    holder.checkBox.setOnClickListener( new View.OnClickListener() {  
		     public void onClick(View v) {  
		      CheckBox cb = (CheckBox) v ;  
		      EventRowData eventRow = (EventRowData) cb.getTag();  
		      Toast.makeText(context.getApplicationContext(),
		       "Clicked on Checkbox: " + cb.getText() +
		       " is " + cb.isChecked(), 
		       Toast.LENGTH_LONG).show();
		      eventRow.setIsChecked(cb.isChecked());
		     }  
		    });  
		   } 
		   else {
		    holder = (ViewHolder) convertView.getTag();
		   }
		 
		   EventRowData eventRow = eventList.get(position);
		   holder.textView.setText(" (" +  eventRow.getText() + ")");
		   holder.checkBox.setText(eventRow.getText());
		   holder.checkBox.setChecked(eventRow.getIsChecked());
		   holder.checkBox.setTag(eventRow);
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
