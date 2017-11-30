package com.poixson.serial.natives;

import com.poixson.serial.DeviceNative;


public abstract class NativeD2xx implements DeviceNative {



	@Override
	public abstract int init();
	@Override
	public abstract int unload();

	@Override
	public abstract void rescanDevices();
	@Override
	public abstract byte[] getDeviceList();

	@Override
	public abstract int openPort(String portName);
	@Override
	public abstract boolean closePort(int handle);

	@Override
	public abstract int setParams(int handle, int baud, int byteSize, int stopBits, int parity, int flags);

	@Override
	public abstract boolean[] getLineStatus(int handle);
	@Override
	public abstract int setLineStatus(int handle, boolean setRTS, boolean setDTR);

	@Override
	public abstract int readBytes(int handle, byte[] bytes, int len);
	@Override
	public abstract int writeBytes(int handle, byte[] bytes);



}
