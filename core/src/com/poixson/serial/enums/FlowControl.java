package com.poixson.serial.enums;


public enum FlowControl {

	FLOW_CONTROL_NONE        (0),
	FLOW_CONTROL_RTSCTS_IN   (1),
	FLOW_CONTROL_RTSCTS_OUT  (2),
	FLOW_CONTROL_XONXOFF_IN  (4),
	FLOW_CONTROL_XONXOFF_OUT (8);



	public static final FlowControl DEFAULT_FLOW_CONTROL = FLOW_CONTROL_NONE;



	public final int value;

	private FlowControl(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}



	public static FlowControl FromInt(final int value) {
		if (value <= 0)
			return null;
		for (final FlowControl flow : FlowControl.values()) {
			if (flow.value == value)
				return flow;
		}
		return null;
	}



}
