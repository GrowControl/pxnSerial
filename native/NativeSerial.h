/* PoiXson pxnSerial 1.x
 * copyright 2017
 * license GPL-3
 * lorenzo at poixson.com
 * http://poixson.com/
 */



#ifndef PXNSERIAL_NATIVE_SERIAL_H
	#define PXNSERIAL_NATIVE_SERIAL_H 1
#endif
#ifndef PXNSERIAL_NATIVE_SERIAL_C
	#define PXNSERIAL_NATIVE_SERIAL_C 1
	#include "NativeSerial.c"
#endif

#include <jni.h>

#include <termios.h>



#define NATIVESERIAL_ERROR_UNKNOWN_FAIL          -1
#define NATIVESERIAL_ERROR_PORT_NOT_FOUND        -2
#define NATIVESERIAL_ERROR_PORT_BUSY             -3
#define NATIVESERIAL_ERROR_PERMISSION_DENIED     -4
#define NATIVESERIAL_ERROR_INCORRECT_SERIAL_PORT -5



/* load/unload */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_init
(JNIEnv *env, jobject obj);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_unload
(JNIEnv *env, jobject obj);



/* list devices */

JNIEXPORT void JNICALL
Java_com_poixson_serial_natives_NativeSerial_rescanDevices
(JNIEnv *env, jobject obj);

JNIEXPORT jobjectArray JNICALL
Java_com_poixson_serial_natives_NativeSerial_getDeviceList
(JNIEnv *env, jobject obj);



/* open/close port */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_openPort
(JNIEnv *env, jobject obj, jstring portName);

JNIEXPORT jboolean JNICALL
Java_com_poixson_serial_natives_NativeSerial_closePort
(JNIEnv *env, jobject obj, jint handle);



/* port parameters */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_setParams
(JNIEnv *env, jobject obj, jint handle, jint baud,
jint byteSize, jint stopBits, jint parity, jint flags);



/* line status */

JNIEXPORT jbooleanArray JNICALL
Java_com_poixson_serial_natives_NativeSerial_getLineStatus
(JNIEnv *env, jobject obj, jint handle);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_setLineStatus
(JNIEnv *env, jobject obj,
jint handle, jboolean setRTS, jboolean setDTR);



/* read/write */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_available
(JNIEnv *env, jobject obj, jint handle);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_pending
(JNIEnv *env, jobject obj, jint handle);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_readBytes
(JNIEnv *env, jobject obj,
jint handle, jbyteArray bytes, jint len);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeSerial_writeBytes
(JNIEnv *env, jobject obj, jint handle, jbyteArray bytes);



speed_t getBaudByNumber(jint baud);
int getByteSizeByNumber(jint byteSize);
