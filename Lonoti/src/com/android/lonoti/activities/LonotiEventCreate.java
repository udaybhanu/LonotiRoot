package com.android.lonoti.activities;

import java.util.Calendar;

import com.android.lonoti.Config;
import com.android.lonoti.R;
import com.android.lonoti.activies.map.MapSelectActivity;
import com.android.lonoti.adapter.PlacesAutoCompleteAdapter;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.network.LonotiAsyncServiceRequest;
import com.android.lonoti.network.ILonotiTaskListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class LonotiEventCreate extends Activity implements OnItemClickListener{

	private PlacesAutoCompleteAdapter adapter;
	
	TextView nameText;
	TextView descriptionText;
	AutoCompleteTextView autoComplete;
	Location location;
	Spinner eventType;
	Button dateButton;
	Button timeButton;
	LinearLayout locationLayout;
	Calendar c = Calendar.getInstance();
	
	LinearLayout layout;
	
	private boolean[] mDaysOfWeek = {false, false, false, false, false, false, false};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lonoti_event_create);
		
		nameText = (TextView) findViewById(R.id.editText1);
		descriptionText = (TextView) findViewById(R.id.editText2);
		
		locationLayout = (LinearLayout) findViewById(R.id.location_layout);
		
		eventType = (Spinner) findViewById(R.id.spinner_event_type);
		
		if("Time Based".equals(eventType.getSelectedItem())){
			locationLayout.setVisibility(View.GONE);
		}else{
			locationLayout.setVisibility(View.VISIBLE);
		}
		
		eventType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String type = arg0.getSelectedItem().toString();
				if("Time Based".equals(type)){
					locationLayout.setVisibility(View.GONE);
				}
				else{
					locationLayout.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		autoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		adapter = new PlacesAutoCompleteAdapter(this, R.layout.auto_complete_item);
		autoComplete.setAdapter(adapter);
		autoComplete.setOnItemClickListener(this);
		
		dateButton = (Button) findViewById(R.id.button_date_select);
		timeButton = (Button) findViewById(R.id.button_time_select);
		
		dateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
			
				DatePickerDialog dateDialog = new DatePickerDialog(LonotiEventCreate.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						String dateString = view.getDayOfMonth() + "/" + view.getMonth() + "/" + view.getYear();
						dateButton.setText(dateString);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				
				dateDialog.show();
				
			}
		});
		
		timeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				TimePickerDialog timeDialog = new TimePickerDialog(LonotiEventCreate.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						String time = view.getCurrentHour() + ":" + view.getCurrentMinute();
						timeButton.setText(time);
					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
				
				timeDialog.show();
			}
		});
		
		Button selectLocationFromMap = (Button) findViewById(R.id.button_select_location);
		
		selectLocationFromMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), MapSelectActivity.class);
				intent.putExtra("lonoti_location_latitude", location.getLat());
				intent.putExtra("lonoti_location_longitude", location.getLon());
				intent.putExtra("lonoti_location_description", location.getLocdescrition());
				startActivityForResult(intent, 1);
			}
		});
		
		layout = (LinearLayout) findViewById(R.id.layout_day_repeat_select);
		
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Builder builder = new Builder(LonotiEventCreate.this);
				builder.setTitle("Repeat");
				builder.setMultiChoiceItems(
						 Config.values, mDaysOfWeek,
			                new DialogInterface.OnMultiChoiceClickListener() {
			                    public void onClick(DialogInterface dialog, int which,
			                            boolean isChecked) {
			                        
			                    }
			                });
				builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						TextView preview = (TextView) layout.findViewById(R.id.repeat_text_days);
						preview.setText(getRepeatString());
					}

				}); 
				builder.show();
				
			}
		});
		
		//startActivityForResult(getIntent(), requestCode)
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Double lattitude = data.getDoubleExtra("lonoti_location_latitude", 0.00);
		Double longitude = data.getDoubleExtra("lonoti_location_longitude", 0.00);
		String description = data.getStringExtra("lonoti_location_description");
		location.setLat(String.valueOf(lattitude));
		location.setLon(String.valueOf(longitude));
		location.setLocdescrition(description);
		autoComplete.setText(description);
		super.onActivityResult(requestCode, resultCode, data);
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
		
		AsyncTask<Object, Integer, Long> execute = new LonotiAsyncServiceRequest(new ILonotiTaskListener() {
			
			@Override
			public void doTask(String response) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void doTask(Location loc) {
				// TODO Auto-generated method stub
				location = loc;
			}
		});
		
		execute.execute("REFERENCE_SEARCH", reference);
		
        //Toast.makeText(this, reference, Toast.LENGTH_SHORT).show();
	}

	
	/*@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putString("name", nameText.getText().toString());
		outState.putString("description", descriptionText.getText().toString());
		outState.putString("locationtext", autoComplete.getText().toString());
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		nameText.setText(savedInstanceState.getString("name"));
		descriptionText.setText(savedInstanceState.getString("description"));
		autoComplete.setText(savedInstanceState.getString("locationtext"));
		
	}*/
	
	private String getRepeatString(){
		
		StringBuffer sb = new StringBuffer();
		
		int j = 0;
		
		for(int i = 0; i < Config.values.length ; i++){
			if(mDaysOfWeek[i]){
				sb.append(Config.values[i].substring(0, 3) + ", ");
				j++;
			}
		}
		
		if( j == 0){
			sb.append("Never");
		} else{
			sb.replace(sb.lastIndexOf(","), sb.length(), "");
		}
		
		return sb.toString();
	}
	
}
