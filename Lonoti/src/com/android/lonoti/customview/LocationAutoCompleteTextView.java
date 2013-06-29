package com.android.lonoti.customview;

import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class LocationAutoCompleteTextView extends AutoCompleteTextView{

	public LocationAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CharSequence convertSelectionToString(Object selectedItem) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
		return hm.get("description");
		
	}
	
}
