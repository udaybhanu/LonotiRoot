package com.android.lonoti.activies.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.lonoti.R;
import com.android.lonoti.R.id;
import com.android.lonoti.R.layout;
import com.android.lonoti.R.menu;
import com.android.lonoti.activities.LonotiEventCreate;
import com.android.lonoti.adapter.PlacesAutoCompleteAdapter;
import com.android.lonoti.adapter.PopupAdapter;
import com.android.lonoti.adapter.data.MarkerContent;
import com.android.lonoti.bom.payload.Location;
import com.android.lonoti.exception.LocationException;
import com.android.lonoti.exception.NetworkException;
import com.android.lonoti.location.LonotiLocationManager;
import com.android.lonoti.location.LonotiLocationPlaces;
import com.android.lonoti.network.LonotiAsyncServiceRequest;
import com.android.lonoti.network.ILonotiTaskListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MapSelectActivity extends FragmentActivity {

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	
	private Marker marker;
	private Circle circle;
	private GoogleMap map;
	private TextView radiusText;
	Map<Marker, MarkerContent> data;
	Location selectedLocation;
	Location presentLocation;
	AutoCompleteTextView mapAutoSearchAutoCompleteTextView;
	PlacesAutoCompleteAdapter adapter;
	String reference;
	private String markerDescription;
	LinearLayout locationRadiusLatlongLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_select);
		
		locationRadiusLatlongLayout = (LinearLayout) findViewById(R.id.location_radius_latlong_layout);
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		
		SupportMapFragment fr = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map));
		
		initMapAutoSearchAutoCompleteTextView();
		
		map = fr.getMap();

		map.setMyLocationEnabled(true);
		
		getPresentLocation();

		String lat = "0";
		String lon = "0";
		
		if(getIntent().hasExtra("lonoti_location_latitude") && getIntent().hasExtra("lonoti_location_description")){
			
			lat = getIntent().getStringExtra("lonoti_location_latitude");
			lon = getIntent().getStringExtra("lonoti_location_description");
			
		}else{
			
			if(presentLocation != null){
			
				lat = presentLocation.getLat();
				lon = presentLocation.getLon();
				
			}
			
		}
		
		markerDescription = getIntent().getStringExtra("lonoti_location_description");
		
		LatLng selectedLocation = new LatLng(Double.valueOf(lat),Double.valueOf(lon));
		
	    Marker selectedMarker = map.addMarker(new MarkerOptions().position(selectedLocation));
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15));

	    // Zoom in, animating the camera.
	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	    
	    data = new HashMap<Marker, MarkerContent>();
		
		MarkerContent c2 = new MarkerContent("1");
		data.put(selectedMarker, c2);
		
		map.setInfoWindowAdapter(new PopupAdapter(this, getLayoutInflater(), data));
		
		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng arg0) {
				// TODO Auto-generated method stub
				
				if(marker != null){
					marker.remove();
				}
				
				marker = map.addMarker(new MarkerOptions().position(arg0));
				data.put(marker, new MarkerContent("3"));
				
				Location location = new Location(String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude), "");
				
				AsyncTask<Object, Integer, Long> execute = new LonotiAsyncServiceRequest(new ILonotiTaskListener() {
					
					@Override
					public void doTask(String response) {
						// TODO Auto-generated method stub
						markerDescription = response;
					}

					@Override
					public void doTask(Location loc) {
						// TODO Auto-generated method stub
					}
				});
				
				execute.execute("LOCATION_SEARCH",location);
				
				/*try {
					markerDescription = LonotiLocationPlaces.getLocationDescription(location);
				} catch (NetworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
			}
		});

		TextView locationText = (TextView) findViewById(R.id.location_text);
		
		locationText.setText(lat + ", " + lon);

		radiusText = (TextView) findViewById(R.id.radius_text);

		SeekBar bar = (SeekBar) findViewById(R.id.seekBar1);
		
		//bar.setMax(48);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				radiusText.setText(String.valueOf((arg1 + 2)/2.0) + "Km");
				if(circle != null){
					circle.remove();
				}
				CircleOptions co = new CircleOptions();
				co.center(marker.getPosition());
				co.radius((arg1+2)*500);
				co.fillColor(Color.TRANSPARENT);
				co.strokeColor(Color.BLUE);
				circle = map.addCircle(co);
				
			}
		});

		
		//SearchView view = findViewById(R.id.s)
		
	}

	private void initMapAutoSearchAutoCompleteTextView() {
		// TODO Auto-generated method stub
		
		mapAutoSearchAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.mapAutoSearchAutoCompleteTextView);
		
		mapAutoSearchAutoCompleteTextView.setThreshold(1);
		
		adapter = new PlacesAutoCompleteAdapter(this, R.layout.auto_complete_item);
		mapAutoSearchAutoCompleteTextView.setAdapter(adapter);
		mapAutoSearchAutoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String str = (String) arg0.getItemAtPosition(arg2);
				reference = adapter.getResults().get(str);
				
				AsyncTask<Object, Integer, Long> execute = new LonotiAsyncServiceRequest(new ILonotiTaskListener() {
					
					@Override
					public void doTask(String response) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void doTask(Location loc) {
						// TODO Auto-generated method stub
						selectedLocation = loc;
						selectedLocation.setReference(reference);
		
		                // Showing the user input location in the Google Map
		                
						Handler handler = new Handler(Looper.getMainLooper());
						
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								double latitude = Double.parseDouble(selectedLocation.getLat());
								double longitude = Double.parseDouble(selectedLocation.getLon());
								
								LatLng selectedPoint = new LatLng(latitude, longitude);
								CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(selectedPoint);
				                CameraUpdate cameraZoom = CameraUpdateFactory.zoomBy(5);
								map.moveCamera(cameraPosition);
				                map.animateCamera(cameraZoom);
				 
				                MarkerOptions options = new MarkerOptions();
				                options.position(selectedPoint);
				                options.title(selectedLocation.getLocdescrition());
				                options.snippet("Latitude:"+latitude+",Longitude:"+longitude);
				 
				                // Adding the marker in the Google Map
				                marker = map.addMarker(options);
				                data.put(marker, new MarkerContent("3"));
							}
						});
						
					}
					
				});
				
				execute.execute("REFERENCE_SEARCH", reference);
				
			}
			
		});
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		if(R.id.done_item == item.getItemId()){
			
			if(marker == null){
				
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				
				alertDialog.setTitle("Select a location");
				alertDialog.setIcon(android.R.drawable.stat_notify_error);
				
				alertDialog.show();
				return false;
				
			} else{
				Intent intent = new Intent();
				intent.putExtra("lonoti_location_latitude", selectedLocation.getLat());
				intent.putExtra("lonoti_location_longitude", selectedLocation.getLon());
				intent.putExtra("lonoti_location_description", selectedLocation.getLocdescrition());
				intent.putExtra("lonoti_location_radius", radiusText.getText().toString());
				setResult(RESULT_OK,intent);
				//startActivity(intent);
				finish();
				return true;
			}
			
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_select, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId() == R.id.show_more_details_item){
			
			if(locationRadiusLatlongLayout.getVisibility() == View.VISIBLE){
				
				locationRadiusLatlongLayout.setVisibility(View.GONE);
				item.setTitle("Show Details");
				
			}else{
				
				locationRadiusLatlongLayout.setVisibility(View.VISIBLE);
				item.setTitle("Hide Details");
				
			}
			
		}
		
		return super.onOptionsItemSelected(item);
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}

	public TextView getRadiusText() {
		return radiusText;
	}

	public void setRadiusText(TextView radiusText) {
		this.radiusText = radiusText;
	}

	public Map<Marker, MarkerContent> getData() {
		return data;
	}

	public void setMarkerDescription(String markerDescription) {
		this.markerDescription = markerDescription;
	}
	
	public String getMarkerDescription() {
		return markerDescription;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		setResult(RESULT_CANCELED);
		super.onBackPressed();
		finish();
	}
	
	private LonotiLocationManager locationManager;
	
	public void getPresentLocation(){
		
		locationManager = LonotiLocationManager.getInstance(this);
		
		try {
			
			android.location.Location inbuiltLocation = map.getMyLocation();
			
			if(inbuiltLocation == null){
				
				locationManager.requestLocation(null, new LocationListener() {
					
					@Override
					public void onStatusChanged(String s, int i, Bundle bundle) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderEnabled(String s) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderDisabled(String s) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLocationChanged(android.location.Location loc) {
						// TODO Auto-generated method stub
						//location = loc
						updatePresentLocationInMap(loc);
						locationManager.unRegisterListener(this);
					}
					
				});

			}else{
				
				updatePresentLocationInMap(inbuiltLocation);
				
			}
						
		} catch (LocationException e) {
			// TODO Auto-generated catch block
			Log.e("LocationException", "No providers found");
		}
		
	}
	

	private void updatePresentLocationInMap(
			android.location.Location loc) {
		// TODO Auto-generated method stub
		
		presentLocation = new Location();
		presentLocation.setLat(String.valueOf(loc.getLatitude()));
		presentLocation.setLon(String.valueOf(loc.getLongitude()));
		
		MarkerContent presentLocationMC = new MarkerContent("1");
		
		if(map != null){
			
			LatLng presentLocationLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
			Marker selectedMarker = map.addMarker(new MarkerOptions().position(presentLocationLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			data.put(selectedMarker, presentLocationMC);
			
			if(selectedLocation == null){
				
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(presentLocationLatLng, 15));
				
			}
			
		}
		
	}
	
}
