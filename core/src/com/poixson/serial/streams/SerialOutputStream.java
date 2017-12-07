package com.poixson.serial.streams;

import java.io.IOException;
import java.io.OutputStream;

import com.poixson.serial.pxnSerial;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class SerialOutputStream extends OutputStream {

	private final pxnSerial serial;



	public SerialOutputStream(final pxnSerial serial) {
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
	public void flush() throws IOException {
	}



	@Override
	public void write(final int b) throws IOException {
		this.serial
			.write(b);
	}
	@Override
	public void write(final byte[] bytes) throws IOException {
		this.serial
			.write(bytes);
	}
	public void write(final byte[] bytes, final int len) throws IOException {
		this.serial
			.write(bytes, len);
	}
	@Override
	public void write(final byte[] bytes, final int offset, final int len) throws IOException {
		this.serial
			.write(bytes, offset, len);
	}



}
