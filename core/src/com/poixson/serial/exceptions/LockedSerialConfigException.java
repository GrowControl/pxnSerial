package com.poixson.serial.exceptions;


public class LockedSerialConfigException extends RuntimeException {
	private static final long serialVersionUID = 1L;



	public LockedSerialConfigException(final String name) {
		super("Cannot changed locked serial config: "+name);
	}



}
