package com.android.lonoti.bom.payload;

public class Login extends Payload{

	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setUsername(String username) {
		this.email = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
