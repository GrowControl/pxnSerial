package com.poixson.nettet.transport;

import java.io.IOException;

import com.poixson.nettet.transports.TransportClient;
import com.poixson.serial.pxnSerial;
import com.poixson.utils.xCloseable;


public class TransportSerial extends TransportClient {

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
