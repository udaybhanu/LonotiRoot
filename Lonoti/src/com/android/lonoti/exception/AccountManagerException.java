package com.android.lonoti.exception;

public class AccountManagerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountManagerException() {
		// TODO Auto-generated constructor stub
		super("AccountManagerException");
	}
	
	public AccountManagerException(String e){
		super("AccountManagerException : " + e);
	}
	
	public AccountManagerException(String e, Throwable t){
		super("AccountManagerException : " + e, t);
	}
	
	public AccountManagerException(Throwable t){
		super("AccountManagerException", t);
	}
	
}
