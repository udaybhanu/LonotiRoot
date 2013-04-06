package com.lonoti.bom;

import com.lonoti.bom.payload.Payload;

public class Request {

	public static String REGISTER = "register";
	public static String LOGIN = "login";
	public static String FLOGIN = "flogin";
	public static String EVENT = "event";
	public static String NOTIFICATION = "notification";
	
	private String type;
	
	private Payload payload;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	
	
}
