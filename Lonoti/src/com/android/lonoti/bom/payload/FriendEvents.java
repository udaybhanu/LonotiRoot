package com.android.lonoti.bom.payload;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "friendsevents")
public class FriendEvents implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
	private Integer id;
	@DatabaseField( foreign = true, foreignAutoRefresh=true)
	private LonotiEvent event;
	@DatabaseField( foreign = true, foreignAutoRefresh=true)
	private Friend friend;
		

	public FriendEvents(){
			
		}
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public FriendEvents(LonotiEvent event,Friend friend){
		this.event = event;
		this.friend = friend;
	}

	public Friend getFriend() {
		return friend;
	}
	public void setFriend(Friend friend) {
		this.friend = friend;
	}
	public LonotiEvent getEvent() {
		return event;
	}
	public void setEvent(LonotiEvent event) {
		this.event = event;
	}
}
