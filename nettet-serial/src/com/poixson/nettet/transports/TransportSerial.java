package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.serial.pxnSerial;


public class TransportSerial extends TransportClient {

	protected final pxnSerial serial;



	public TransportSerial(final pxnSerial serial) {
		this.serial = serial;
	}



	@Override
	public void connect() throws IOException {
		this.serial
			.open();
	}
	@Override
	public void close() throws IOException {
		this.serial
			.close();
	}



	@Override
	public boolean isConnected() {
		return
			this.serial
				.isOpen();
	}
	@Override
	public boolean isClosed() {
		return
			this.serial
				.isClosed();
	}



}
