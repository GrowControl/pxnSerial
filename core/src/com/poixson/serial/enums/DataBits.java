package com.poixson.serial.enums;

import com.poixson.utils.NumberUtils;


public enum DataBits {

	DATA_BITS_4 (4),
	DATA_BITS_5 (5),
	DATA_BITS_6 (6),
	DATA_BITS_7 (7),
	DATA_BITS_8 (8),
	DATA_BITS_9 (9);



	public static final DataBits DEFAULT_BYTE_SIZE = DATA_BITS_8;



	public final int value;

	private DataBits(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}



	public static DataBits FromInt(final int value) {
		if (value <= 0)
			return null;
		for (final DataBits byteSize : DataBits.values()) {
			if (byteSize.value >= value)
				return byteSize;
		}
		return null;
	}
	public static DataBits FromString(final String str) {
		final Integer val = NumberUtils.toInteger(str);
		return (
			val == null
			? null
			: FromInt(val.intValue())
		);
	}



}
