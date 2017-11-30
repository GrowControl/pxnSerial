package com.poixson.serial;


public interface DeviceNative {


	public int init();
	public int unload();

	public String getErrorMsg(final int handle);

	public void rescanDevices();
	public byte[] getDeviceList();

	public int openPort(String portName);
	public boolean closePort(int handle);

	public int setParams(int handle, int baud, int byteSize, int stopBits, int parity, int flags);

	public boolean[] getLineStatus(int handle);
	public int setLineStatus(int handle, boolean setRTS, boolean setDTR);

	public int available(int handle);
	public int pending(int handle);

	public int readBytes(int handle, byte[] bytes, int len);
	public int writeBytes(int handle, byte[] bytes);


}
