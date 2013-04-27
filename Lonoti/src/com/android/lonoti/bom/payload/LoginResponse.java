package com.android.lonoti.bom.payload;

public class LoginResponse extends Payload{

	private String email;
	private String auth_token;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuth_token() {
		return auth_token;
	}
	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}
	
}
