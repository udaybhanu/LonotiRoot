package com.android.lonoti.exception;

public class UIException extends Exception{

	private static final long serialVersionUID = 1L;

	public UIException() {
		// TODO Auto-generated constructor stub
		super("UIException");
	}
	
	public UIException(String e){
		super("UIException : " + e);
	}
	
	public UIException(String e, Throwable t){
		super("UIException : " + e, t);
	}
	
	public UIException(Throwable t){
		super("UIException", t);
	}
	
}
