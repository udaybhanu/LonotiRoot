package com.android.lonoti.activities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.lonoti.Config;
import com.android.lonoti.R;
import com.android.lonoti.activies.map.MapSelectActivity;
import com.android.lonoti.adapter.ContactAdapter;
import com.android.lonoti.adapter.PlacesAutoCompleteAdapter;
import com.android.lonoti.adapter.data.Contact;
import com.android.lonoti.bom.payload.Friend;
import com.android.lonoti.bom.payload.FriendEvents;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.bom.payload.LonotiEvent;
import com.android.lonoti.bom.payload.TimeEvent;
import com.android.lonoti.customview.ContactsMultiAutoCompleteView;
import com.android.lonoti.dbhelper.DatabaseManager;
import com.android.lonoti.network.LonotiAsyncServiceRequest;
import com.android.lonoti.network.ILonotiTaskListener;
import com.android.lonoti.network.data.LonotiEventServerData;
import com.android.lonoti.network.data.LonotiEventServerData.LonotiEventServerFriends;
import com.android.lonoti.network.data.LonotiEventServerData.LonotiEventServerLocation;
import com.android.lonoti.network.data.LonotiEventServerData.LonotiEventServerPayload;
import com.android.lonoti.network.data.LonotiEventServerData.LonotiEventServerTime;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class LonotiEventCreate extends Activity{

	//private PlacesAutoCompleteAdapter adapter;
	
	TextView nameText;
	TextView descriptionText;
	//AutoCompleteTextView autoComplete;
	Location location;
	String reference;
	Spinner eventType;
	Button dateButton;
	Button timeButton;
	//LinearLayout locationLayout;
	LinearLayout locationLayout2;
	LinearLayout locationDescLayout;
	Calendar c = Calendar.getInstance();
	int checkedItem;
	Activity activity;
	LinearLayout layout;
	TimePicker timePicker;
	DatePicker datePicker;
	LinearLayout datePickerLayout;
	LinearLayout timePickerLayout;
	Spinner templateListView;
	Map<String, String> TemplateMap;
	EditText messageText;
	Button selectMapButton;
	Button buttonSave;
	Button buttonCancel;
	TextView locationDescTextView;
	TextView locationDetailsTextView;
	ContactsMultiAutoCompleteView contactsMultiAutoCompleteView1;
	List<Contact> contacts;
	LonotiEvent event;
	
	boolean[] mDaysOfWeek = {false, false, false, false, false, false, false};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lonoti_event_create);
		activity = this;
		
		if(getIntent().hasExtra("event_id")){
			
			Integer event_id = getIntent().getIntExtra("event_id", -1);
			
			if(event_id != -1){
				
				event = DatabaseManager.getInstance().getLonotiEvent(event_id);
				
			}
			
		}
		
		nameText = (TextView) findViewById(R.id.editText1);
		
		descriptionText = (TextView) findViewById(R.id.editText2);
		
		if(event != null){
			
			nameText.setText(event.getName());
			descriptionText.setText(event.getDescription());
		}
		
		locationLayout2 = (LinearLayout) findViewById(R.id.location_layout);
		locationDescLayout = (LinearLayout) findViewById(R.id.location_det_layout);
		locationDescTextView = (TextView) findViewById(R.id.locationDescTextView);
		locationDetailsTextView = (TextView) findViewById(R.id.locationDetailsTextView);
		
		//Setting the contacts
		contactsMultiAutoCompleteView1 = (ContactsMultiAutoCompleteView) findViewById(R.id.contactsMultiAutoCompleteView1);
		
		if(contacts == null){
			contacts = getContacts();
		}
		
		contactsMultiAutoCompleteView1.setAdapter(new ContactAdapter(this, R.layout.number_item_layout, contacts));
		contactsMultiAutoCompleteView1.setTokenizer(new CommaTokenizer());
		//---End of setting the contacts
		
		//contactsMultiAutoCompleteView1.setText(event.get)
		
		selectMapButton = (Button) findViewById(R.id.selectMapButton);
		
		eventType = (Spinner) findViewById(R.id.spinner_event_type);
		
		if(event != null){
			if(event.getLocation() == null){
				eventType.setSelection(0);
			}else{
				eventType.setSelection(1);
			}
		}
		
		if("Time Based".equals(eventType.getSelectedItem())){
			locationLayout2.setVisibility(View.GONE);
		}else{
			locationLayout2.setVisibility(View.VISIBLE);
		}
		
		eventType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String type = arg0.getSelectedItem().toString();
				if("Time Based".equals(type)){
					locationLayout2.setVisibility(View.GONE);
				}
				else{
					locationLayout2.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		dateButton = (Button) findViewById(R.id.button_date_select);
		timeButton = (Button) findViewById(R.id.button_time_select);
		
		if(event != null){
			dateButton.setText(Config.dateFormat.format(event.getTime().getNotDate()));
			timeButton.setText(event.getTime().getDuration()/60 + ":" + event.getTime().getDuration()%60);
		}
		
		datePickerLayout = (LinearLayout) activity.findViewById(R.id.datePickerLayout);
		timePickerLayout = (LinearLayout) activity.findViewById(R.id.timePickerLayout);
		
		datePicker = (DatePicker) datePickerLayout.findViewById(R.id.datePickerEvent);
		dateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
			
				/*DatePickerDialog dateDialog = new DatePickerDialog(LonotiEventCreate.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						String dateString = view.getDayOfMonth() + "/" + view.getMonth() + "/" + view.getYear();
						dateButton.setText(dateString);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				
				dateDialog.show();*/
				
				
				if(datePickerLayout.getVisibility() == View.VISIBLE){
					datePickerLayout.setVisibility(View.GONE);
					dateButton.setText(datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear());
				}else{
					String text = dateButton.getText().toString();
					if(text.equals("Select Date")){
						datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 
								
							new OnDateChangedListener(){

								@Override
								public void onDateChanged(DatePicker arg0,
										int arg1, int arg2, int arg3) {
									// TODO Auto-generated method stub
									dateButton.setText(arg3 + "/" + arg2 + "/" + arg1);
								}
							
						});
					}else{
						String[] da = text.split("/");
						Integer day = Integer.parseInt(da[0]);
						Integer month = Integer.parseInt(da[1]);
						Integer year = Integer.parseInt(da[2]);
						datePicker.updateDate(year, month, day);
					}
					datePickerLayout.setVisibility(View.VISIBLE);
					timePickerLayout.setVisibility(View.GONE);
				}
				
			}
		});
		
		timePicker = (TimePicker) timePickerLayout.findViewById(R.id.timePickerEvent);
		
		timeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				/*TimePickerDialog timeDialog = new TimePickerDialog(LonotiEventCreate.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						String time = view.getCurrentHour() + ":" + view.getCurrentMinute();
						timeButton.setText(time);
					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
				
				timeDialog.show();*/
				
				timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

					@Override
					public void onTimeChanged(TimePicker arg0, int arg1,
							int arg2) {
						// TODO Auto-generated method stub
						timeButton.setText(arg1 + ":" + arg2);
					}
					
				});
				
				if(timePickerLayout.getVisibility() == View.VISIBLE){
					timePickerLayout.setVisibility(View.GONE);
					timeButton.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
				}else{
					String text = timeButton.getText().toString();
					if(text.equals("Select Time")){
						timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
						timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
					}else{
						Integer hour = Integer.parseInt(text.split(":")[0]);
						Integer minute = Integer.parseInt(text.split(":")[1]);
						timePicker.setCurrentHour(hour);
						timePicker.setCurrentMinute(minute);
					}
					timePickerLayout.setVisibility(View.VISIBLE);
					datePickerLayout.setVisibility(View.GONE);
				}
				
			}
		});
		
		selectMapButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), MapSelectActivity.class);
				if(location != null) {
				
					intent.putExtra("lonoti_location_latitude", location.getLat());
					intent.putExtra("lonoti_location_longitude", location.getLon());
					intent.putExtra("lonoti_location_description", location.getLocdescrition());
					
				}
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
				
				//builder.setSingleChoiceItems(Config.values, checkedItem, null);
				
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
						//int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
						//checkedItem = selectedPosition;
						TextView preview = (TextView) layout.findViewById(R.id.repeat_text_days);
						preview.setText(getRepeatString());
					}
				}); 
				builder.show();
			}
		});
		
		messageText = (EditText) findViewById(R.id.messageText);
		
		templateListView = (Spinner) findViewById(R.id.templateList);
		
		templateListView.setAdapter(new ArrayAdapter<String>(activity, R.layout.text_view, getTemplateList()));
		templateListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				String type = arg0.getSelectedItem().toString();
				String text = getTemplateMap().get(type);
				includeImageSpans(text);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//startActivityForResult(getIntent(), requestCode)
		
		ImageView locationImage = (ImageView) findViewById(R.id.imageView1);
		
		locationImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				addLocationImage();
			}
		});
		
		
		/*buttonSave = (Button) findViewById(R.id.button_event_save);
		
		if(event != null){
			buttonSave.setText("Update");
		}
		
		buttonSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveEvent();
			}
		});*/
		
		if(event != null){
			
			includeImageSpans(event.getMessage());
			
		}
		
	}

	
	private List<Contact> getContacts() {
		// TODO Auto-generated method stub
		List<Contact> contacts = new ArrayList<Contact>();
		
		Cursor c = getContentResolver().query(android.provider.ContactsContract.Data.CONTENT_URI, new String[] {Phone.NUMBER, Contacts.DISPLAY_NAME, Phone.TYPE}, 
				Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'", null, null);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			Contact contact = new Contact();
			contact.setNumber(c.getString(c.getColumnIndex(Phone.NUMBER)));
			contact.setName(c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME)));
			contact.setType(getPhoneType(c.getInt(c.getColumnIndex(Phone.TYPE))));
			System.out.println(contact.getNumber() + " " + contact.getName() + " " + contact.getType());
			contacts.add(contact);
		}
		
		return contacts;
	}


	private String getPhoneType(int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case Phone.TYPE_HOME:
			return "Home";
		case Phone.TYPE_WORK:
			return "Work";
		case Phone.TYPE_MOBILE:
			return "Mobile";
		default:
			return "";
		}
	}


	protected boolean saveEvent() {
		// TODO Auto-generated method stub
		
		// Save into DB
		
		boolean invalidData = false;
		
		event = new LonotiEvent();
		TimeEvent time = new TimeEvent();
		event.setEnabled(true);
		LonotiEventServerData data = new LonotiEventServerData();
		
		data.setAction(0);
		data.setStatus(0);
		
		LonotiEventServerPayload payload = data.new LonotiEventServerPayload();
		LonotiEventServerTime serverTime = data.new LonotiEventServerTime();
		
		String name = nameText.getText().toString();
		
		if(name.length() == 0){
			invalidData = true;
			nameText.setError("Name is required");
		}else{
			event.setName(name);
			payload.setTitle(name);
		}
		
		
		String description = descriptionText.getText().toString();
		
		if(description.length() == 0){
			//invalidData = true;
			//descriptionText.setError("Description is required");
		}else{
			event.setDescription(description);
		}
		
		String message = messageText.getText().toString();
		
		if(message.length() == 0){
			invalidData = true;
			messageText.setError("Message is required");
		}else{
			event.setMessage(message);
			payload.setMessage(message);
		}
		
		String date = dateButton.getText().toString();
		String timedata = timeButton.getText().toString();
		String finalTime = "";
		
		if(date.length() != 0 && date.equals("Select Date")){
			invalidData = true;
			dateButton.setError("Date is required");
		}else{
			finalTime = finalTime + date;
			try {
				Date dateExtracted = Config.dateFormat.parse(date);
				serverTime.setDate_sec(dateExtracted.getTime());
				time.setNotDate(dateExtracted);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				invalidData = true;
				dateButton.setError("Date is invalid");
			}
		}
		
		if(timedata.length() != 0 && timedata.equals("Select Time")){
			invalidData = true;
			timeButton.setError("Time is required");
		}else{
			finalTime = finalTime + " " + timedata; 
			try {
				Date dateExtracted = Config.timeFormat.parse(timedata);
				Integer timeInminutes = dateExtracted.getHours()*60 + dateExtracted.getMinutes();
				serverTime.setTrigger_time(timeInminutes);
				time.setDuration(timeInminutes);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				invalidData = true;
				timeButton.setError("Time is invalid");
			}
		}
		
		if(!finalTime.equals("")){
			
			event.setTime(time);
			
		}
		
		if(!"Time Based".equals(eventType.getSelectedItem())){
			
			if(location == null){
				invalidData = true;
				selectMapButton.setError("Location is required");
			}else{
				
				payload.setType(1);
				LonotiEventServerLocation serLocation = data.new LonotiEventServerLocation();
				serLocation.setLat(Double.valueOf(location.getLat()));
				serLocation.setLon(Double.valueOf(location.getLon()));
				serLocation.setDistance(Integer.parseInt(location.getDistance().replace("Km", "")));
				payload.setLocation(serLocation);
				event.setLocation(location);
			}
			
		}else{
			
			payload.setType(0);
			
		}
		
		serverTime.setRepeats_on_week(convertRepeat());
		
		payload.setTime(serverTime);

		
		/*LonotiEventServerFriends friend = data.new LonotiEventServerFriends();
		friend.setEmail("mrudhu@gmail.com");
		friend.setPhone_number("9177023915");*/
		if(!invalidData){
			DatabaseManager.getInstance().createLonotiEvent(event);
		
		
		if(contactsMultiAutoCompleteView1.getText().length() == 0){
			
			invalidData = true;
			contactsMultiAutoCompleteView1.setError("Enter atleast one contact");
			
		}else{
			
			List<String> enteredContacts = resolveContacts(contactsMultiAutoCompleteView1.getText().toString());
			
			for(String enteredContact:enteredContacts){
				
				LonotiEventServerFriends friend = data.new LonotiEventServerFriends();
				friend.setEmail("");
				friend.setPhone_number(enteredContact);
				payload.getFriends().add(friend);
				
				Friend friendFromDB = DatabaseManager.getInstance().getFriendByNumber(enteredContact);
				
				if(friendFromDB == null){
					
					friendFromDB = new Friend();
					friendFromDB.setEmailId("");
					friendFromDB.setMobileNumber(enteredContact);
					friendFromDB.setIsAppUser(true);
					DatabaseManager.getInstance().createFriend(friendFromDB);
					
				}
				
				FriendEvents newFriendEvent = new FriendEvents(event, friendFromDB);
				DatabaseManager.getInstance().createFriendEvents(newFriendEvent);
				
			}
			
		}
		}
		
		//payload.getFriends().add(friend);

		
		if(invalidData){
			Toast.makeText(this, "Invalid Data", 400);
			return false;
		}
		
		
		// Send it to server
		data.setPayload(payload);
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(data);

		Log.i("Json Request : ", jsonRequest);
		
		System.out.println(jsonRequest);
		
		LonotiAsyncServiceRequest asyncRequet = new LonotiAsyncServiceRequest(new ILonotiTaskListener() {
			
			@Override
			public void doTask(Location location) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void doTask(String response) {
				// TODO Auto-generated method stub
				System.out.println("Response : " + response);
				
				JSONObject jobj;
				try {
					jobj = new JSONObject(response);
					String server_event_id = jobj.getString("event_id");
					event.setServer_event_id(server_event_id);
					DatabaseManager.getInstance().updateLonotiEvent(event);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		String url = Config.EVENT_NEW_URL;
		String serverPayload = null;
		
			//System.out.println(new String(Base64.encodeToString(jsonRequest.getBytes(), Base64.NO_WRAP)));
			serverPayload = new String(Base64.encodeToString(jsonRequest.getBytes(), Base64.NO_WRAP));
			//serverPayload = URLEncoder.encode(new String(Base64.encodeToString(jsonRequest.getBytes(), Base64.DEFAULT)), "UTF-8");
		
		asyncRequet.execute(url, "POST", 30000, true, "data=" + serverPayload);
		
		//Toast.makeText(this, "Event Saved", 400);
		
		Intent intent = null;
		intent = new Intent(this, Home2Activity.class);
		this.startActivity(intent);
		this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		this.finish();
		return true;
	}
	
	
	private List<String> resolveContacts(String text) {
		// TODO Auto-generated method stub
		
		List<String> enteredContacts = new ArrayList<String>();
		
		for(String eachContact:text.split(",")){
			
			if(eachContact.length() > 2 && eachContact.indexOf(">") != -1 && eachContact.indexOf("<") != -1){
				
				String number = eachContact.substring(eachContact.indexOf(">") + 1);
				number = number.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
				enteredContacts.add(number);
				
			}
			
		}
		
		return enteredContacts;
	}


	private String convertRepeat(){
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < mDaysOfWeek.length - 1 ; i ++ ){
			if(mDaysOfWeek[i]){
				sb.append(i + ",");
			}
		}
		
		if(!sb.toString().equals("")){
			return sb.substring(0, sb.length() - 1);
		}
		
		return "";
	}


	private void includeImageSpans(String text){
		
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		Bitmap image1 = BitmapFactory.decodeResource( getResources(), android.R.drawable.ic_menu_mylocation);
		
		String editTextString = text;
		//String editTextString = ssb.toString();
		
		while(editTextString.indexOf(Config.LOCATION_ICON_STRING) != -1){
			//System.out.println(loc.substring(0, loc.indexOf("%LOC%")));
			//System.out.println(loc.substring(loc.indexOf("%LOC%") + "%LOC%".length()));
			int index = ssb.length();
			ssb.append(editTextString.substring(0, editTextString.indexOf(Config.LOCATION_ICON_STRING)) + Config.LOCATION_ICON_STRING);
			ssb.setSpan(new ImageSpan(image1), index + editTextString.indexOf(Config.LOCATION_ICON_STRING), index + editTextString.indexOf(Config.LOCATION_ICON_STRING) + Config.LOCATION_ICON_STRING.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			editTextString = editTextString.substring(editTextString.indexOf(Config.LOCATION_ICON_STRING) + Config.LOCATION_ICON_STRING.length());
			//editTextString.substring(0, editTextString.indexOf(LOCATION_ICON_STRING)) + 
			System.out.println(editTextString);
			
		}
		
		ssb.append(editTextString);
		
		messageText.setText(ssb);
		
	}
	
	private void addLocationImage(){
		
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		Bitmap image1 = BitmapFactory.decodeResource( getResources(), android.R.drawable.ic_menu_mylocation);
		
		String editTextString = messageText.getText().subSequence(0, messageText.getSelectionStart()) + Config.LOCATION_ICON_STRING;
		if(messageText.getSelectionStart() < messageText.getText().length()){
			editTextString = editTextString + messageText.getText().subSequence(messageText.getSelectionStart(), messageText.getText().length());
		}
		//String editTextString = ssb.toString();
		
		while(editTextString.indexOf(Config.LOCATION_ICON_STRING) != -1){
			//System.out.println(loc.substring(0, loc.indexOf("%LOC%")));
			//System.out.println(loc.substring(loc.indexOf("%LOC%") + "%LOC%".length()));
			int index = ssb.length();
			ssb.append(editTextString.substring(0, editTextString.indexOf(Config.LOCATION_ICON_STRING)) + Config.LOCATION_ICON_STRING);
			ssb.setSpan(new ImageSpan(image1), index + editTextString.indexOf(Config.LOCATION_ICON_STRING), index + editTextString.indexOf(Config.LOCATION_ICON_STRING) + Config.LOCATION_ICON_STRING.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			editTextString = editTextString.substring(editTextString.indexOf(Config.LOCATION_ICON_STRING) + Config.LOCATION_ICON_STRING.length());
			//editTextString.substring(0, editTextString.indexOf(LOCATION_ICON_STRING)) + 
			System.out.println(editTextString);
			
		}
		
		ssb.append(editTextString);
		
		messageText.setText(ssb);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if(resultCode == RESULT_OK){
			
			String lattitude = data.getStringExtra("lonoti_location_latitude");
			String longitude = data.getStringExtra("lonoti_location_longitude");
			String description = data.getStringExtra("lonoti_location_description");
			String radius = data.getStringExtra("lonoti_location_radius");
			location = new Location();
			location.setLat(lattitude);
			location.setLon(longitude);
			location.setLocdescrition(description);
			location.setDistance(radius);
			
			locationDescTextView.setText(description);
			locationDetailsTextView.setText(lattitude + ", " + longitude + " ( "+ radius +" ) ");
			locationDescLayout.setVisibility(View.VISIBLE);
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lonoti_event_create, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		if(R.id.item_save == item.getItemId()){
			
			saveEvent();
			return true;
			
		}else{
			return super.onMenuItemSelected(featureId, item);
		}
		
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
		} else if( j == 7){
			return "Every day";
		}
		else{
			sb.replace(sb.lastIndexOf(","), sb.length(), "");
		}
		
		return sb.toString();
		
		//return Config.values[checkedItem];
		
	}
	
	
	private List<String> getTemplateList(){
		List<String> templateList = new ArrayList<String>();
		templateList.add("New Template");
		templateList.add("Template1");
		templateList.add("Template2");
		return templateList;
	}
	
	private Map<String, String> getTemplateMap(){
		
		TemplateMap = new HashMap<String, String>();
		TemplateMap.put("New Template", "");
		TemplateMap.put("Template1", "Location: %LOC%");
		TemplateMap.put("Template2", "Location: %LOC% and my Location is again %LOC%");
		return TemplateMap;
		
	}
	
}
