package com.poixson.serial.streams;

import java.io.IOException;
import java.io.InputStream;

import com.poixson.serial.pxnSerial;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class SerialInputStream extends InputStream {

	private final pxnSerial serial;



	public SerialInputStream(final pxnSerial serial) {
		if (serial == null) throw RequiredArgumentException.getNew("serial");
		this.serial = serial;
	}



	@Override
	public void close() throws IOException {
		try {
			super.close();
		} finally {
			this.serial.close();
		}
	}



	@Override
	public int available() {
		return this.serial.available();
	}



	@Override
	public int read() throws IOException {
		return this.serial
			.read();
	}
	@Override
	public int read(byte bytes[]) throws IOException {
		return this.serial
			.read(bytes);
	}
	public int read(byte[] bytes, final int len) throws IOException {
		return this.serial
			.read(bytes, len);
	}
	@Override
	public int read(byte[] bytes, final int offset, final int len) throws IOException {
		return this.serial
			.read(bytes, offset, len);
	}



}
