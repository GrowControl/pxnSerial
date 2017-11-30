package com.poixson.serial.natives;


public class NativeD2xxOpen extends NativeD2xx {



	@Override
	public native int init();
	@Override
	public native int unload();

	@Override
	public String getErrorMsg(final int handle) {
return null;
	}

	@Override
	public native void rescanDevices();
	@Override
	public native byte[] getDeviceList();

	@Override
	public native int openPort(String portName);
	@Override
	public native boolean closePort(int handle);

	@Override
	public native int setParams(int handle, int baud, int byteSize, int stopBits, int parity, int flags);

	@Override
	public native boolean[] getLineStatus(int handle);
	@Override
	public native int setLineStatus(int handle, boolean setRTS, boolean setDTR);

	@Override
	public native int available(int handle);
	@Override
	public native int pending(int handle);

	@Override
	public native int readBytes(int handle, byte[] bytes, int len);
	@Override
	public native int writeBytes(int handle, byte[] bytes);



}
