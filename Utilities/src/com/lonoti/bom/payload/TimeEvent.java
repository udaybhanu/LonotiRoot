package com.lonoti.bom.payload;

import java.util.Date;

public class TimeEvent {

	private Date notDate;
	private String duration;
	
	public Date getNotDate() {
		return notDate;
	}
	public void setNotDate(Date notDate) {
		this.notDate = notDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}
