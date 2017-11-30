package com.poixson.serial.natives;

import com.poixson.serial.DeviceNative;


public class NativeSerial implements DeviceNative {

	public static final int NATIVESERIAL_ERROR_UNKNOWN_FAIL          = -1;
	public static final int NATIVESERIAL_ERROR_PORT_NOT_FOUND        = -2;
	public static final int NATIVESERIAL_ERROR_PORT_BUSY             = -3;
	public static final int NATIVESERIAL_ERROR_PERMISSION_DENIED     = -4;
	public static final int NATIVESERIAL_ERROR_INCORRECT_SERIAL_PORT = -5;


	@Override
	public native int init();
	@Override
	public native int unload();

	@Override
	public String getErrorMsg(final int handle) {
		if (handle > 0)
			return "Port is open";
		switch (handle) {
		case 0:
			return "Port is closed";
		case NATIVESERIAL_ERROR_UNKNOWN_FAIL:
			return "Unknown failure";
		case NATIVESERIAL_ERROR_PORT_NOT_FOUND:
			return "Port not found";
		case NATIVESERIAL_ERROR_PORT_BUSY:
			return "Port is busy";
		case NATIVESERIAL_ERROR_PERMISSION_DENIED:
			return "Permission denied to port";
		case NATIVESERIAL_ERROR_INCORRECT_SERIAL_PORT:
			return "Invalid serial port";
		}
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
