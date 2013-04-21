package com.android.lonoti.exception;

public class LocationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocationException() {
		// TODO Auto-generated constructor stub
		super("LocationException");
	}
	
	public LocationException(String e){
		super("LocationException : " + e);
	}
	
	public LocationException(String e, Throwable t){
		super("LocationException : " + e, t);
	}
	
	public LocationException(Throwable t){
		super("LocationException", t);
	}
	
}
