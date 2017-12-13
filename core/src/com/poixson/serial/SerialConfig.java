package com.poixson.serial;

import com.poixson.serial.enums.Baud;
import com.poixson.serial.enums.DataBits;
import com.poixson.serial.enums.FlowControl;
import com.poixson.serial.enums.Parity;
import com.poixson.serial.enums.StopBits;
import com.poixson.serial.exceptions.LockedSerialConfigException;
import com.poixson.serial.natives.NativeSerial;
import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class SerialConfig {

	public static final boolean DEFAULT_RTS   = false;
	public static final boolean DEFAULT_DTR   = false;
	public static final int     DEFAULT_FLAGS = 0;

	public volatile String      portName = null;
	public volatile Baud        baud     = null;
	public volatile DataBits    byteSize = null;
	public volatile StopBits    stopBits = null;
	public volatile Parity      parity   = null;
	public volatile FlowControl flow     = null;
	public volatile Boolean     rts      = null;
	public volatile Boolean     dtr      = null;
	public volatile Integer     flags    = null;

	private volatile boolean locked = false;



	public SerialConfig(final SerialConfig cfg) {
		this(
			cfg.portName,
			cfg.baud,
			cfg.byteSize,
			cfg.stopBits,
			cfg.parity,
			cfg.flow,
			cfg.rts,
			cfg.dtr,
			cfg.flags
		);
	}
	public SerialConfig(final String portName) {
		this(
			portName,
			null      // baud
		);
	}
	public SerialConfig(final String portName, final int baudInt) {
		this(
			portName,
			Baud.FromInt(baudInt)
		);
		if (this.baud == null) throw new RuntimeException("Invalid baud rate: "+Integer.toString(baudInt));
	}
	public SerialConfig(final String portName, final Baud baud) {
		this(
			portName,
			baud,
			null, // byteSize
			null, // stopBits
			null, // parity
			null, // flow control
			null, // rts
			null, // dtr
			null  // flags
		);
	}
	public SerialConfig(
			final String portName, final Baud baud,
			final DataBits byteSize, final StopBits stopBits,
			final Parity parity, final FlowControl flowControl,
			final Boolean rts, final Boolean dtr, final Integer flags) {
		this(
			portName,
			baud,
			byteSize,
			stopBits,
			parity,
			flowControl,
			( rts   == null ? DEFAULT_RTS   : rts.booleanValue() ),
			( dtr   == null ? DEFAULT_DTR   : dtr.booleanValue() ),
			( flags == null ? DEFAULT_FLAGS : flags.intValue()   )
		);
	}
	public SerialConfig(
			final String portName, final Baud baud,
			final DataBits byteSize, final StopBits stopBits,
			final Parity parity, final FlowControl flowControl,
			final boolean rts, final boolean dtr, final int flags) {
		this();
		this.portName = portName;
		this.baud     = baud;
		this.byteSize = byteSize;
		this.stopBits = stopBits;
		this.parity   = parity;
		this.flow     = flowControl;
		this.rts      = Boolean.valueOf(rts);
		this.dtr      = Boolean.valueOf(dtr);
		this.flags    = Integer.valueOf(flags);
	}
	public SerialConfig() {
	}
	@Override
	public SerialConfig clone() {
		return new SerialConfig(this);
	}



	public pxnSerial build() {
		return
			new pxnSerial(
				this
			);
	}
	public DeviceNative getNative() {
//TODO: enum or something
return new NativeSerial();
	}



	// lock
	public SerialConfig lock() {
		this.locked = true;
		return this;
	}
	public boolean isLocked() {
		return this.locked;
	}



	// port name
	public String getPortName() {
		final String portName = this.portName;
		if (Utils.isEmpty(portName)) throw RequiredArgumentException.getNew("portName");
		return portName;
	}
	public SerialConfig setPortName(final String portName) {
		if (this.isLocked()) throw new LockedSerialConfigException("portName");
		this.portName = portName;
		return this;
	}



	// baud
	public Baud getBaud() {
		final Baud baud = this.baud;
		return (
			baud == null
			? Baud.DEFAULT_BAUD
			: baud
		);
	}
	public int getBaudValue() {
		return
			this.getBaud()
				.getValue();
	}
	public int getBaudInt() {
		return
			this.getBaud()
				.getValue();
	}
	public String getBaudStr() {
		return
			this.getBaud()
				.toString();
	}
	public SerialConfig setBaud(final int baudInt) {
		if (this.isLocked()) throw new LockedSerialConfigException("baud");
		return
			this.setBaud(
				Baud.FromInt(baudInt)
			);
	}
	public SerialConfig setBaud(final Baud baud) {
		if (this.isLocked()) throw new LockedSerialConfigException("baud");
		this.baud = baud;
		return this;
	}



	// byte size (data bits)
	public DataBits getByteSize() {
		final DataBits byteSize = this.byteSize;
		return (
			byteSize == null
			? DataBits.DEFAULT_BYTE_SIZE
			: byteSize
		);
	}
	public int getByteSizeValue() {
		return
			this.getByteSize()
				.getValue();
	}
	public SerialConfig setByteSize(final int byteSizeInt) {
		if (this.isLocked()) throw new LockedSerialConfigException("byteSize");
		return
			this.setByteSize(
				DataBits.FromInt(byteSizeInt)
			);
	}
	public SerialConfig setByteSize(final DataBits byteSize) {
		if (this.isLocked()) throw new LockedSerialConfigException("byteSize");
		this.byteSize = byteSize;
		return this;
	}



	// stop bits
	public StopBits getStopBits() {
		final StopBits stopBits = this.stopBits;
		return (
			stopBits == null
			? StopBits.DEFAULT_STOP_BITS
			: stopBits
		);
	}
	public int getStopBitsValue() {
		return
			this.getStopBits()
				.getValue();
	}
	public SerialConfig setStopBits(final double stopBitsDbl) {
		if (this.isLocked()) throw new LockedSerialConfigException("stopBits");
		return
			this.setStopBits(
				StopBits.FromDouble(stopBitsDbl)
			);
	}
	public SerialConfig setStopBits(final int stopBitsInt) {
		if (this.isLocked()) throw new LockedSerialConfigException("stopBits");
		return
			this.setStopBits(
				StopBits.FromInt(stopBitsInt)
			);
	}
	public SerialConfig setStopBits(final StopBits stopBits) {
		if (this.isLocked()) throw new LockedSerialConfigException("stopBits");
		this.stopBits = stopBits;
		return this;
	}



	// parity
	public Parity getParity() {
		final Parity parity = this.parity;
		return (
			parity == null
			? Parity.DEFAULT_PARITY
			: parity
		);
	}
	public int getParityValue() {
		return
			this.getParity()
				.getValue();
	}
	public SerialConfig setParity(final Parity parity) {
		if (this.isLocked()) throw new LockedSerialConfigException("parity");
		this.parity = parity;
		return this;
	}



	// flow control
	public FlowControl getFlowControl() {
		final FlowControl flow = this.flow;
		return (
			flow == null
			? FlowControl.DEFAULT_FLOW_CONTROL
			: flow
		);
	}
	public SerialConfig setFlowControl(final FlowControl flow) {
		if (this.isLocked()) throw new LockedSerialConfigException("flowControl");
		this.flow = flow;
		return this;
	}



	// rts
	public boolean getRTS() {
		final Boolean rts = this.rts;
		return (
			rts == null
			? DEFAULT_RTS
			: rts.booleanValue()
		);
	}
	public SerialConfig setRTS(final boolean rts) {
		if (this.isLocked()) throw new LockedSerialConfigException("RTS");
		return
			this.setRTS(
				Boolean.valueOf(rts)
			);
	}
	public SerialConfig setRTS(final Boolean rts) {
		if (this.isLocked()) throw new LockedSerialConfigException("RTS");
		this.rts = rts;
		return this;
	}



	// dtr
	public boolean getDTR() {
		final Boolean dtr = this.dtr;
		return (
			dtr == null
			? DEFAULT_DTR
			: dtr.booleanValue()
		);
	}
	public SerialConfig setDTR(final boolean dtr) {
		if (this.isLocked()) throw new LockedSerialConfigException("DTR");
		return
			this.setDTR(
				Boolean.valueOf(dtr)
			);
	}
	public SerialConfig setDTR(final Boolean dtr) {
		if (this.isLocked()) throw new LockedSerialConfigException("DTR");
		this.dtr = dtr;
		return this;
	}



	// flag bits
	public int getFlags() {
		final Integer val = this.flags;
		if (val == null)
			return DEFAULT_FLAGS;
		final int flags = val.intValue();
		if (flags < 0) throw new IllegalArgumentException("Invalid flags value: "+Integer.toString(flags));
		return flags;
	}
	public SerialConfig setFlags(final int flags) {
		if (this.isLocked()) throw new LockedSerialConfigException("flags");
		return
			this.setFlags(
				Integer.valueOf(flags)
			);
	}
	public SerialConfig setFlags(final Integer flags) {
		if (this.isLocked()) throw new LockedSerialConfigException("flags");
		this.flags = flags;
		return this;
	}
	public SerialConfig setFlag(final int flag) {
		if (this.isLocked()) throw new LockedSerialConfigException("flags");
		this.flags = Integer.valueOf(
			this.flags.intValue() | flag
		);
		return this;
	}
	public SerialConfig unsetFlag(final int flag) {
		if (this.isLocked()) throw new LockedSerialConfigException("flags");
		this.flags = Integer.valueOf(
			this.flags.intValue() & (~flag)
		);
		return this;
	}



}
