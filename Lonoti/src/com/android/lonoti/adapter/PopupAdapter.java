package com.android.lonoti.adapter;

import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.lonoti.R;
import com.android.lonoti.R.id;
import com.android.lonoti.R.layout;
import com.android.lonoti.activies.map.MapSelectActivity;
import com.android.lonoti.adapter.data.MarkerContent;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PopupAdapter implements InfoWindowAdapter{

	private LayoutInflater inflater;
	private Map<Marker, MarkerContent> data;
	private Activity activity;
	
	public PopupAdapter(Activity activity, LayoutInflater inflater, Map<Marker, MarkerContent> data) {
		// TODO Auto-generated constructor stub
		this.inflater = inflater;
		this.data = data;
		this.activity = activity;
	}
	
	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		// TODO Auto-generated method stub
		MarkerContent content = data.get(marker);
		String id = content.getId();
		TextView view = (TextView) inflater.inflate(R.layout.text_view, null);
		
		view.setText(id);
		
		TextView locationText = (TextView) activity.findViewById(R.id.location_text);
		
		locationText.setText((float)Math.round(marker.getPosition().latitude*100)/100 + ", " + (float)Math.round(marker.getPosition().longitude*100)/100);
		
		SeekBar bar = (SeekBar) activity.findViewById(R.id.seekBar1);
		bar.setVisibility(View.VISIBLE);
		
		((MapSelectActivity) activity).setMarker(marker);
		
		return view;
	}

}
