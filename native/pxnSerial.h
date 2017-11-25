/* PoiXson pxnSerial 1.x
 * copyright 2017
 * license GPL-3
 * lorenzo at poixson.com
 * http://poixson.com/
 */



#ifndef MAX_DEVICES
	#define MAX_DEVICES 64
#endif
#ifndef SERIAL_SIZE
	#define SERIAL_SIZE 16
#endif



#ifndef PXNSERIAL_NATIVES
	#define PXNSERIAL_NATIVES 1
	#include "pxnSerial.c"
#endif



// interface types
#include "NativeSerial.h"
#include "NativeD2xxOpen.h"
#include "NativeD2xxProp.h"
