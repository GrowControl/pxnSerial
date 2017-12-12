package com.poixson.serial;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import com.poixson.serial.enums.SerialState;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;
import com.poixson.utils.xCloseable;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class pxnSerial implements xCloseable {

	public static final int DEFAULT_STRING_BUFFER_SIZE = 4096;

	public final SerialConfig cfg;
	public final DeviceNative nat;

	protected volatile int    handle   = 0;
	protected volatile String errorMsg = null;

	protected final ReentrantLock stateLock = new ReentrantLock();
	protected final ReentrantLock readLock  = new ReentrantLock();
	protected final ReentrantLock writeLock = new ReentrantLock();

	private byte[] tmpSingleReadByte  = new byte[1];
	private byte[] tmpSingleWriteByte = new byte[1];



	public pxnSerial(final SerialConfig cfg) {
		if (cfg == null) throw RequiredArgumentException.getNew("cfg");
		this.cfg = cfg.clone().lock();
		this.nat = cfg.getNative();
	}
	public void finalize() {
		Utils.safeClose(this);
		this.nat.unload();
	}



	// ------------------------------------------------------------------------------- //
	// open/close port



	@SuppressWarnings("resource")
	public void open() throws IOException {
		if ( ! this.stateLock.tryLock()) {
			this.setErrorMsg("Already opening port");
			throw new IOException("Already opening port "+this.getPortName());
		}
		try {
			// check port state
			switch (this.getState()) {
			case OPEN:
				this.setErrorMsg("Port already open");
				throw new IOException("Port already open "+this.getPortName());
			case ERROR:
				Utils.safeClose(this);
			default:
			}
			// open the port
			final int h =
				this.nat.openPort(
					this.getPortName()
				);
			// check state
			{
				final SerialState state = SerialState.valueOf(h);
				switch (state) {
				case ERROR:
				case CLOSED:
					this.handle = h;
					final String errMsg =
						this.nat.getErrorMsg(h);
					this.setErrorMsg(errMsg);
					throw new IOException(
						(new StringBuilder())
							.append("Failed to open port ")
							.append(this.getPortName())
							.append(" (h:").append(h)
							.append(
								Utils.isEmpty(errMsg)
								? ")"
								: ") "+errMsg
							)
							.toString()
					);
				case OPEN:
				default:
				}
			}
			// configure the port
			{
				final int result =
					this.nat.setParams(
						h,
						this.cfg.getBaudValue(),
						this.cfg.getByteSizeValue(),
						this.cfg.getStopBitsValue(),
						this.cfg.getParityValue(),
						this.cfg.getFlags()
					);
				// failed to set params
				if (result <= 0) {
					Utils.safeClose(this);
					this.handle = result;
					this.setErrorMsg("Failed to configure serial port");
					throw new IOException(
						(new StringBuilder())
							.append("Failed to configure serial port: ")
							.append(result)
							.append(" ")
							.append(this.getPortName())
							.toString()
					);
				}
			}
			// set line status
			{
				final int result =
					this.nat.setLineStatus(
						h,
						this.cfg.getRTS(),
						this.cfg.getDTR()
					);
				// failed to set line status
				if (result <= 0) {
					this.handle = result;
					this.setErrorMsg("Failed to set serial line status");
					throw new IOException(
						(new StringBuilder())
							.append("Failed to set serial line status: ")
							.append(result)
							.append(" ")
							.append(this.getPortName())
							.toString()
					);
				}
			}
			this.handle = h;
		} finally {
			this.stateLock.unlock();
		}
	}



	@Override
	public void close() throws IOException {
		this.ensureOpenAndLock();
		try {
			if (this.isOpen()) {
				this.flush();
			}
			this.nat.closePort(this.handle);
			this.handle   = 0;
			this.errorMsg = null;
		} finally {
			this.stateLock.unlock();
		}
	}



	// ------------------------------------------------------------------------------- //
	// port state



	public SerialState getState() {
		return
			SerialState.valueOf(
				this.getHandle()
			);
	}
	public int getHandle() {
		this.stateLock.lock();
		try {
			return this.handle;
		} finally {
			this.stateLock.unlock();
		}
	}



	public boolean isOpen() {
		return
			SerialState.OPEN.equals(
				this.getState()
			);
	}
	@Override
	public boolean isClosed() {
		return
			SerialState.CLOSED.equals(
				this.getState()
			);
	}



	protected void ensureOpenAndLock() throws IOException {
		this.stateLock.lock();
		if ( ! this.isOpen()) {
			this.stateLock.unlock();
			throw new IOException("Port is not open "+this.getPortName());
		}
	}



	public String getErrorMsg() {
		return this.errorMsg;
	}
	public void setErrorMsg(final String errMsg) {
		this.errorMsg = errMsg;
	}



	public SerialConfig getConfig() {
		return this.cfg;
	}
	public DeviceNative getNative() {
		return this.nat;
	}



	public String getPortName() {
		return this.cfg.portName;
	}



	// ------------------------------------------------------------------------------- //
	// read



	public int available() {
		try {
			this.ensureOpenAndLock();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		try {
			return this.nat.available(this.handle);
		} finally {
			this.stateLock.unlock();
		}
	}
	public boolean hasAvailable() {
		return (this.available() > 0);
	}



	public int read() throws IOException {
		final int result =
			this.read(
				this.tmpSingleReadByte,
				0,
				1
			);
		if (result < 0) throw new IOException();
		return this.tmpSingleReadByte[1];
	}
	public int read(byte bytes[]) throws IOException {
		return
			this.read(
				bytes,
				0,
				bytes.length
			);
	}
	public int read(byte[] bytes, final int len) throws IOException {
		return
			this.read(
				bytes,
				0,
				len
			);
	}
	public int read(byte[] bytes, final int offset, final int len) throws IOException {
		if (bytes == null) throw new NullPointerException();
		if (offset < 0) throw new IllegalArgumentException("offset cannot be less than 0");
		if (len    < 1) throw new IllegalArgumentException("len cannot be less than 1");
		if (bytes.length < offset + len) throw new IllegalArgumentException("offset/len out of range");
		this.ensureOpenAndLock();
		try {
			this.readLock.lock();
			try {
//TODO: add the offset argument
				return
					this.nat.readBytes(
						this.handle,
						bytes,
						len
					);
			} finally {
				this.readLock.unlock();
			}
		} finally {
			this.stateLock.unlock();
		}
	}



	public String readString() throws IOException {
		return this.readString(DEFAULT_STRING_BUFFER_SIZE);
	}
	public String readString(final int maxLength) throws IOException {
		byte[] bytes = new byte[maxLength];
		final int result =
			this.read(
				bytes,
				0,
				maxLength
			);
		if (result < 0) throw new IOException();
		if (result == 0)
			return "";
		return
			new String(
				bytes,
				0,      // offset
				result, // length
				StringUtils.UTF8
			);
	}



	// ------------------------------------------------------------------------------- //
	// write



	public void flush() throws IOException {
//TODO:
//		this.nat.flush();
	}



	public void write(final int b) throws IOException {
		this.tmpSingleWriteByte[0] = (byte) b;
		this.write(
			this.tmpSingleWriteByte,
			0,
			1
		);
	}
	public void write(final byte[] bytes) throws IOException {
		this.write(
			bytes,
			0,
			bytes.length
		);
	}
	public void write(final byte[] bytes, final int len) throws IOException {
		this.write(
			bytes,
			0,
			len
		);
	}
	public void write(final byte[] bytes, final int offset, final int len) throws IOException {
		if (bytes == null) throw new NullPointerException();
		if (offset < 0) throw new IllegalArgumentException("offset cannot be less than 0");
		if (len    < 1) throw new IllegalArgumentException("len cannot be less than 1");
		if (bytes.length < offset + len) throw new IllegalArgumentException("offset/len out of range");
		this.ensureOpenAndLock();
		try {
			this.writeLock.lock();
			try {
//TODO: add the offset and len arguments
				this.nat.writeBytes(this.handle, bytes);
			} finally {
				this.writeLock.unlock();
			}
		} finally {
			this.stateLock.unlock();
		}
	}



	public void writeString(final String msg) throws IOException {
		if (Utils.isEmpty(msg)) throw new NullPointerException();
		final byte[] bytes =
			msg.getBytes(StringUtils.UTF8);
		this.write(bytes);
	}



}
