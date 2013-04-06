package com.lonoti.bom;

import com.lonoti.bom.payload.Payload;

public class Response {

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
