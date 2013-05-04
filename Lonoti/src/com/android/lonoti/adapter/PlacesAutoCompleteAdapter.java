package com.android.lonoti.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.android.lonoti.exception.NetworkException;
import com.android.lonoti.location.LonotiLocationPlaces;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> resultList;
    private Map<String, String> results;
    
    public Map<String, String> getResults() {
		return results;
	}

	public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    
    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    
						//resultList = LonotiLocationPlaces.getContent(constraint.toString());
                    	try {
							results = LonotiLocationPlaces.getContentMap(constraint.toString());
						} catch (NetworkException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					                    // Assign the data to the FilterResults
                    resultList = new ArrayList<String>();
                    resultList.addAll(results.keySet());
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}