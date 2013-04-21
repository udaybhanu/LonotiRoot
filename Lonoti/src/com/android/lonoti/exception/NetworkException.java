package com.android.lonoti.exception;

public class NetworkException extends Exception{

	private static final long serialVersionUID = 1L;

	public NetworkException() {
		// TODO Auto-generated constructor stub
		super("NetworkException");
	}
	
	public NetworkException(String e){
		super("NetworkException : " + e);
	}
	
	public NetworkException(String e, Throwable t){
		super("NetworkException : " + e, t);
	}
	
	public NetworkException(Throwable t){
		super("NetworkException", t);
	}
	
}
