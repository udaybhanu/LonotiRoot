package com.android.lonoti.exception;

public class DBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBException() {
		// TODO Auto-generated constructor stub
		super("DBException");
	}
	
	public DBException(String e){
		super("DBException : " + e);
	}
	
	public DBException(String e, Throwable t){
		super("DBException : " + e, t);
	}
	
	public DBException(Throwable t){
		super("DBException", t);
	}
	
}
