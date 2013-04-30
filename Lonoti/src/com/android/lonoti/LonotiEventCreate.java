package com.android.lonoti;

import com.android.lonoti.adapter.PlacesAutoCompleteAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class LonotiEventCreate extends Activity implements OnItemClickListener{

	private PlacesAutoCompleteAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lonoti_event_create);
		
		AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		adapter = new PlacesAutoCompleteAdapter(this, R.layout.auto_complete_item);
		autoComplete.setAdapter(adapter);
		autoComplete.setOnItemClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lonoti_event_create, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String str = (String) arg0.getItemAtPosition(arg2);
		String reference = adapter.getResults().get(str);
        Toast.makeText(this, reference, Toast.LENGTH_SHORT).show();
	}

}
