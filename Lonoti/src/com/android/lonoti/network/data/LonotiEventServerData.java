package com.android.lonoti.network.data;

import java.util.ArrayList;

public class LonotiEventServerData {

	private Integer action;
	private Integer event_id;
	private Integer status;
	private LonotiEventServerPayload payload;
	
	public LonotiEventServerPayload getPayload() {
		return payload;
	}
	
	public void setPayload(LonotiEventServerPayload payload) {
		this.payload = payload;
	}
	
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public Integer getEvent_id() {
		return event_id;
	}
	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public class LonotiEventServerPayload{
		
		private Integer type;
		private String title;
		private String message;
		private LonotiEventServerTime time;
		private LonotiEventServerLocation location;
		private ArrayList<LonotiEventServerFriends> friends = new ArrayList<LonotiEventServerData.LonotiEventServerFriends>();
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public LonotiEventServerTime getTime() {
			return time;
		}
		public void setTime(LonotiEventServerTime time) {
			this.time = time;
		}
		public LonotiEventServerLocation getLocation() {
			return location;
		}
		public void setLocation(LonotiEventServerLocation location) {
			this.location = location;
		}
		public ArrayList<LonotiEventServerFriends> getFriends() {
			return friends;
		}
		public void setFriends(ArrayList<LonotiEventServerFriends> friends) {
			this.friends = friends;
		}
		
	}
	
	public class LonotiEventServerTime{
		
		private Long date_sec;
		private Integer trigger_time;
		private boolean send_location;
		private String repeats_on_week;
		
		public Long getDate_sec() {
			return date_sec;
		}
		public void setDate_sec(Long date_sec) {
			this.date_sec = date_sec;
		}
		public Integer getTrigger_time() {
			return trigger_time;
		}
		public void setTrigger_time(Integer trigger_time) {
			this.trigger_time = trigger_time;
		}
		public boolean isSend_location() {
			return send_location;
		}
		public void setSend_location(boolean send_location) {
			this.send_location = send_location;
		}
		public String getRepeats_on_week() {
			return repeats_on_week;
		}
		public void setRepeats_on_week(String repeats_on_week) {
			this.repeats_on_week = repeats_on_week;
		}
		
	}
	
	public class LonotiEventServerLocation{
	
		private Double lat;
		private Double lon;
		private Integer distance;
		
		public Double getLat() {
			return lat;
		}
		public void setLat(Double lat) {
			this.lat = lat;
		}
		public Double getLon() {
			return lon;
		}
		public void setLon(Double lon) {
			this.lon = lon;
		}
		public Integer getDistance() {
			return distance;
		}
		public void setDistance(Integer distance) {
			this.distance = distance;
		}
		
	}
	
	public class LonotiEventServerFriends{
		
		private String mobilenumber;
		private String email;
		public String getMobilenumber() {
			return mobilenumber;
		}
		public void setMobilenumber(String mobilenumber) {
			this.mobilenumber = mobilenumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
	}
}
