package com.android.lonoti.network;

import com.android.lonoti.bom.payload.Location;

public interface ILonotiTaskListener {

	public void doTask(String response);

	public void doTask(Location location);
	
}
