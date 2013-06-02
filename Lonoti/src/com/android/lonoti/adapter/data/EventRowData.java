package com.android.lonoti.adapter.data;

import com.android.lonoti.bom.payload.LonotiEvent;

public class EventRowData {

	private String text;
	private Boolean isChecked;
	private LonotiEvent event;
	
	public EventRowData(String text, Boolean isChecked) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.isChecked = isChecked;
	}
	
	public LonotiEvent getEvent() {
		return event;
	}
	
	public void setEvent(LonotiEvent event) {
		this.event = event;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
