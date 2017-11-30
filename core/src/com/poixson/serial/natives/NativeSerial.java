package com.poixson.serial.natives;

import com.poixson.serial.DeviceNative;


public class NativeSerial implements DeviceNative {



	@Override
	public native int init();
	@Override
	public native int unload();

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
	public native int readBytes(int handle, byte[] bytes, int len);
	@Override
	public native int writeBytes(int handle, byte[] bytes);



}
