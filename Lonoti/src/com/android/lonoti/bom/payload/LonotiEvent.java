package com.android.lonoti.bom.payload;

import java.util.ArrayList;

import java.util.List;

import com.android.lonoti.dbhelper.DatabaseManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "levents")
public class LonotiEvent extends Payload{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
	private Integer id;
	
	@DatabaseField
	private String name;
	@DatabaseField
	private String description;
	@DatabaseField( foreign = true, foreignAutoRefresh=true)
	private Location location;
	@DatabaseField
	private TimeEvent time;
	
	@DatabaseField
	private String message;
	
	@ForeignCollectionField
    public ForeignCollection<FriendEvents> friendEvents;
	
	//If the values here are to be associated with the values in Friends table
	//We will have to use Foreign Object.
	//Again there is many-to-many mapping between 
	/*@DatabaseField(dataType=DataType.SERIALIZABLE) //, foreign = true)
	private List<Friend> friends = new ArrayList<Friend>();*/
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ForeignCollection<FriendEvents> getFriendEvents() {
		return friendEvents;
	}

	public LonotiEvent(){
		//needed by ormlite
	}
	
	public LonotiEvent(	String name, String description, Location location){
		this.name = name;
		this.description = description;
		this.location = location;
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		List<Friend> friends = new ArrayList<Friend>();
		for(FriendEvents fe : this.getFriendEvents())
		{
			friends.add(fe.getFriend());
		}
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		for(Friend friend : friends)
		{
			FriendEvents fe = new FriendEvents(this, friend);
			DatabaseManager.getInstance().createFriendEvents(fe);
		}
	}
	
}
