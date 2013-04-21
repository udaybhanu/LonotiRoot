package com.android.lonoti.bom.payload;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "friendsevents")
public class FriendEvents {
	
	@DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
	private Integer id;
	@DatabaseField( foreign = true, foreignAutoRefresh=true)
	private LEvent event;
	@DatabaseField( foreign = true, foreignAutoRefresh=true)
	private Friend friend;
		

	public FriendEvents(){
			
		}
	public FriendEvents(LEvent event,Friend friend){
		this.event = event;
		this.friend = friend;
	}

	public Friend getFriend() {
		return friend;
	}
	public void setFriend(Friend friend) {
		this.friend = friend;
	}
	public LEvent getEvent() {
		return event;
	}
	public void setEvent(LEvent event) {
		this.event = event;
	}
}
