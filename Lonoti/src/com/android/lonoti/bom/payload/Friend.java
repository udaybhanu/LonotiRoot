package com.android.lonoti.bom.payload;

import java.io.Serializable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lfriends")
public class Friend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
	private Integer id;
	
	@DatabaseField(unique = true)
	private String mobileNumber;
	@DatabaseField
	private Boolean isAppUser;
	@DatabaseField
	private String emailId;
	
	@ForeignCollectionField
    public ForeignCollection<FriendEvents> friendEvents;
	
	public Friend(){
		
	}
	public Friend(String mobileNumber, Boolean isAppUser,	String emailId){
		this.mobileNumber = mobileNumber;
		this.isAppUser = isAppUser;
		this.emailId = emailId;
 	}
	
	public ForeignCollection<FriendEvents> getFriendEvents() {
		return friendEvents;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Boolean getIsAppUser() {
		return isAppUser;
	}
	public void setIsAppUser(Boolean isAppUser) {
		this.isAppUser = isAppUser;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
