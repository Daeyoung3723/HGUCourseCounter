package edu.handong.analysise.utils;

public class NotEnoughArgumentException extends Exception  {
	
	public NotEnoughArgumentException() {
		super("Not Enough Argument Exception"); 
	}
	
	public NotEnoughArgumentException(String message) {
		super(message); 
	}


	
}
