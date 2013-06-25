package com.android.lonoti.bom;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lUserData")
public class UserData {

	@DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
	private Integer Id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private String email;
	
	@DatabaseField
	private String phonenumber;
	
	@DatabaseField
	private boolean termsAccepted;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public boolean isTermsAccepted() {
		return termsAccepted;
	}

	public void setTermsAccepted(boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}
	
	
	
}
