package com.poixson.serial;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.AbstractSelectableChannel;

import com.poixson.serial.enums.Baud;
import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;

import sun.nio.ch.IOUtil;
import sun.nio.ch.Net;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;


public class pxnSerialChannel extends AbstractSelectableChannel implements SelChImpl {

	private static final int VALID_OPS = (OP_READ | OP_WRITE);

	protected final pxnSerial serial;

	protected final FileDescriptor fd;
	protected final int            handle;



	public static pxnSerialChannel open(final String portName, final int baudInt) throws IOException {
		final Baud baud = Baud.FromInt(baudInt);
		if (baud == null) throw new IllegalArgumentException("Invalid baud: "+Integer.toString(baudInt));
		return open(
			portName,
			baud
		);
	}
	public static pxnSerialChannel open(final String portName, final Baud baud) throws IOException {
		if (Utils.isEmpty(portName)) throw RequiredArgumentException.getNew("portName");
		if (baud == null)            throw RequiredArgumentException.getNew("baud");
		final SerialConfig cfg =
			(new SerialConfig())
				.setPortName(portName)
				.setBaud(baud);
		final pxnSerial serial =
			new pxnSerial(cfg);
		return open(serial);
	}
	public static pxnSerialChannel open(final pxnSerial serial)
			throws IOException {
		return new pxnSerialChannel(serial);
	}



	protected pxnSerialChannel(final pxnSerial serial) throws IOException {
		super(null);
		if (serial == null) throw RequiredArgumentException.getNew("serial");
		this.serial = serial;
		// ensure port is open
		if ( ! serial.isOpen()) {
			serial.open();
		}
		this.handle = serial.getHandle();
		this.fd     = IOUtil.newFD(this.handle);
	}



	public pxnSerial serial() {
		return this.serial;
	}



	@Override
	public FileDescriptor getFD() {
		return this.fd;
	}
	@Override
	public int getFDVal() {
		return this.handle;
	}



	// interrupt thread
	@Override
	public void kill() throws IOException {
//TODO: is this needed?
	}
	@Override
	protected void implCloseSelectableChannel() throws IOException {
		this.serial
			.close();
	}



	@Override
	protected void implConfigureBlocking(final boolean block) throws IOException {
//TODO:
//		this.serial.setBlocking(blocking);
	}



	@Override
	public int validOps() {
		return VALID_OPS;
	}



	// ------------------------------------------------------------------------------- //
	// read



	public int available() {
		return this.serial
				.available();
	}
	public boolean hasAvailable() {
		return this.serial
				.hasAvailable();
	}



	public int read() throws IOException {
		return this.serial
				.read();
	}
	public int read(byte bytes[]) throws IOException {
		return this.serial
				.read(bytes);
	}
	public int read(byte[] bytes, final int len) throws IOException {
		return this.serial
				.read(bytes, len);
	}
	public int read(byte[] bytes, final int offset, final int len) throws IOException {
		return this.serial
				.read(bytes, offset, len);
	}



	public String readString() throws IOException {
		return this.serial
				.readString();
	}
	public String readString(final int maxLength) throws IOException {
		return this.serial
				.readString(maxLength);
	}



	// ------------------------------------------------------------------------------- //
	// write



	public void flush() throws IOException {
		this.serial
			.flush();
	}



	public void write(final int b) throws IOException {
		this.serial
			.write(b);
	}
	public void write(final byte[] bytes) throws IOException {
		this.serial
			.write(bytes);
	}
	public void write(final byte[] bytes, final int len) throws IOException {
		this.serial
			.write(bytes, len);
	}
	public void write(final byte[] bytes, final int offset, final int len) throws IOException {
		this.serial
			.write(bytes, offset, len);
	}



	// ------------------------------------------------------------------------------- //



	@Override
	public boolean translateAndUpdateReadyOps(final int options, final SelectionKeyImpl skey) {
		return
			translateReadyOps(
				options,
				skey.nioReadyOps(),
				skey
			);
	}
	@Override
	public boolean translateAndSetReadyOps(final int options, final SelectionKeyImpl skey) {
		return
			translateReadyOps(
				options,
				0,
				skey
			);
	}
	@Override
	public void translateAndSetInterestOps(final int options, final SelectionKeyImpl skey) {
		int newOps = 0;
		if ( 0 != (options & SelectionKey.OP_READ) )
			newOps |= Net.POLLIN;
		if ( 0 != (options & SelectionKey.OP_WRITE) )
			newOps |= Net.POLLOUT;
//		if ( 0 != (options & SelectionKey.OP_CONNECT) )
//			newOps |= Net.POLLCONN;
		skey.selector.putEventOps(skey, newOps);
	}
	public boolean translateReadyOps(final int options, final int initialOps, final SelectionKeyImpl sKey) {
		if ( (options & Net.POLLNVAL) != 0)
			return false;
		int intOptions = sKey.nioInterestOps();
		int oldOptions = sKey.nioReadyOps();
		int newOptions = initialOps;
		if ((options & (Net.POLLERR | Net.POLLHUP)) != 0) {
			newOptions = intOptions;
			sKey.nioReadyOps(newOptions);
			return ((newOptions & ~oldOptions) != 0);
		}
		if ( (options & Net.POLLIN) != 0) {
			if ( (intOptions & SelectionKey.OP_READ) != 0) {
				if (this.isOpen()) {
					newOptions |= SelectionKey.OP_READ;
				}
			}
		}
		if ( (options & Net.POLLOUT) != 0) {
			if ( (intOptions & SelectionKey.OP_WRITE) != 0) {
				if (this.isOpen()) {
					newOptions |= SelectionKey.OP_WRITE;
				}
			}
		}
		sKey.nioReadyOps(newOptions);
		return ( (newOptions & ~oldOptions) != 0);
	}



	public void writeString(final String msg) throws IOException {
		this.serial
			.writeString(msg);
	}



}
