package com.lonoti.bom.payload;

import java.util.ArrayList;
import java.util.List;

public class Event extends Payload{

	private String name;
	private String description;
	private Location location;
	private TimeEvent time;
	private List<Friend> friends = new ArrayList<Friend>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public TimeEvent getTime() {
		return time;
	}

	public void setTime(TimeEvent time) {
		this.time = time;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	
}
