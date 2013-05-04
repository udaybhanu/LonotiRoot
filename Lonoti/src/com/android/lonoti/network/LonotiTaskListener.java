package com.android.lonoti.network;

import com.android.lonoti.bom.payload.Location;

public interface LonotiTaskListener {

	public void doTask(String response);

	public void doTask(Location location);
	
}
