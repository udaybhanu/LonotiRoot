package com.android.lonoti.gcm;

import java.util.HashMap;
import java.util.Map;

public enum GcmCollapseKey {
	
	GET_LOCATION("lonoti_get_location"),
	SYNC_DB("lonoti_sync_db");
	
	private String key;
	
	public static Map<String, GcmCollapseKey> keySet = new HashMap<String,GcmCollapseKey>();
	
	static 
	{
		for (GcmCollapseKey keyObject : GcmCollapseKey.values())
		{
			keySet.put(keyObject.key, keyObject);
		}
	}
	
	public static GcmCollapseKey getKey(String value)
	{
		return keySet.get(value);
	}
	
	private GcmCollapseKey(String key) {
		this.key = key;
	}

}
