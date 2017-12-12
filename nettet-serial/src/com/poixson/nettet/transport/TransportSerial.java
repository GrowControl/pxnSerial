package com.poixson.nettet.transport;

import java.io.IOException;

import com.poixson.nettet.transports.Transport;
import com.poixson.serial.pxnSerial;
import com.poixson.utils.xCloseable;


public class TransportSerial implements Transport, xCloseable {

	protected final pxnSerial serial;



	public TransportSerial(final pxnSerial serial) {
		this.serial = serial;
	}



	public void Open() {
	}



	@Override
	public void close() throws IOException {
	}



	@Override
	public boolean isClosed() {
return false;
	}



}
