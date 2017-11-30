package com.poixson.serial.enums;


public enum SerialState {

	CLOSED ( 0),
	ERROR  (-1),
	OPEN   (Integer.MAX_VALUE);



	public final int value;

	SerialState(final int value) {
		this.value = value;
	}



	public static SerialState valueOf(final int handle) {
		if (handle == 0)
			return CLOSED;
		if (handle > 0)
			return OPEN;
		return ERROR;
	}



}
