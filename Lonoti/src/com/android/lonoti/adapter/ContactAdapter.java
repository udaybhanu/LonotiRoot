package com.android.lonoti.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.lonoti.R;
import com.android.lonoti.adapter.data.Contact;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<Contact> implements Filterable{

	private List<Contact> contactList;
	private Activity activity;
	private List<Contact> filteredList;
	
	public ContactAdapter(Activity activity, int resource, List<Contact> objects) {
		super(activity, resource, objects);
		this.activity = activity;
		this.contactList = objects;
		// TODO Auto-generated constructor stub
	}

	public List<Contact> getContactList() {
		return contactList;
	}
	
	public List<Contact> getFilteredList() {
		return filteredList;
	}
	
	@Override
	public Contact getItem(int position) {
		// TODO Auto-generated method stub
		
		if(filteredList == null){
			return null;
		}
		
		return filteredList.get(position);
	}
	
	/*public static class ViewHolder{
        public TextView nameTextView;
        public TextView phoneTypeTextView;
        public TextView phoneNumberTextView;
    }
	*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View v = convertView;
		//ViewHolder holder;
		
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.number_item_layout, null);
			//holder = new ViewHolder();
			TextView nameTextView = (TextView) v.findViewById(R.id.name_textview);
			TextView phoneTypeTextView = (TextView) v.findViewById(R.id.phone_type_textview);
			TextView phoneNumberTextView = (TextView) v.findViewById(R.id.phone_number_textview);
		
		
		final Contact contact = filteredList.get(position);
		if(contact != null){
			
			nameTextView.setText(contact.getName());
			phoneTypeTextView.setText(contact.getType());
			phoneNumberTextView.setText(contact.getNumber());
			
		/*	
			holder.nameTextView.setText(contact.getName());
			holder.phoneTypeTextView.setText(contact.getType());
			holder.phoneNumberTextView.setText(contact.getNumber());
		*/	
		}
		return v;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return filteredList.size();
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter(){

			@Override
			protected FilterResults performFiltering(CharSequence charsequence) {
				// TODO Auto-generated method stub
				
				if(charsequence == null){
					FilterResults results = new FilterResults();
					results.count = 0;
					results.values = new ArrayList<Contact>();
					return results;
				}
				
				List<Contact> finalList = new ArrayList<Contact>();
				for(Contact contact:contactList){
					
					if(contact.getName().toLowerCase().indexOf(charsequence.toString().toLowerCase()) != -1){
						finalList.add(contact);
					}
					
				}
				
				FilterResults results = new FilterResults();
				results.count = finalList.size();
				results.values = finalList;
				return results;
			}

			@Override
			protected void publishResults(CharSequence charsequence,
					FilterResults filterresults) {
				// TODO Auto-generated method stub
				if (filterresults.count > 0) {
	                filteredList = (List<Contact>) filterresults.values;
	                notifyDataSetChanged();
	            } else {
	                notifyDataSetInvalidated();
	            }
			}
			
		};
	}
	
}

