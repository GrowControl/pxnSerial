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
	public abstract long openPort(String portName);
	@Override
	public abstract boolean closePort(long handle);

	@Override
	public abstract long setParams(long handle, int baud, int byteSize, int stopBits, int parity, int flags);

	@Override
	public abstract boolean[] getLineStatus(long handle);
	@Override
	public abstract long setLineStatus(long handle, boolean setRTS, boolean setDTR);

	@Override
	public abstract int readBytes(long handle, byte[] bytes, int len);
	@Override
	public abstract long writeBytes(long handle, byte[] bytes);



}
