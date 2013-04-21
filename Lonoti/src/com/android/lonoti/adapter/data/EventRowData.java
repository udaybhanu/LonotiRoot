package com.android.lonoti.adapter.data;

public class EventRowData {

	private String text;
	private Boolean isChecked;
	
	public EventRowData(String text, Boolean isChecked) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.isChecked = isChecked;
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
