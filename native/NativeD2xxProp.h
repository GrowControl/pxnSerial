/* PoiXson pxnSerial 1.x
 * copyright 2017
 * license GPL-3
 * lorenzo at poixson.com
 * http://poixson.com/
 */



#ifndef PXNSERIAL_NATIVE_D2XX_PROP_H
	#define PXNSERIAL_NATIVE_D2XX_PROP_H 1
#endif
#ifndef PXNSERIAL_NATIVE_D2XX_PROP_C
	#define PXNSERIAL_NATIVE_D2XX_PROP_C 1
	#include "NativeD2xxProp.c"
#endif

#include <jni.h>



/* load/unload */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_init
(JNIEnv *env, jobject obj);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_unload
(JNIEnv *env, jobject obj);



/* list devices */

JNIEXPORT void JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_rescanDevices
(JNIEnv *env, jobject obj);

JNIEXPORT jobjectArray JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_getDeviceList
(JNIEnv *env, jobject obj);



/* open/close port */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_openPort
(JNIEnv *env, jobject obj, jstring portName);

JNIEXPORT jboolean JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_closePort
(JNIEnv *env, jobject obj, jint handle);



/* port parameters */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_setParams
(JNIEnv *env, jobject obj, jint handle, jint baud,
jint byteSize, jint stopBits, jint parity, jint flags);



/* line status */

JNIEXPORT jbooleanArray JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_getLineStatus
(JNIEnv *env, jobject obj, jint handle);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_setLineStatus
(JNIEnv *env, jobject obj,
jint handle, jboolean setRTS, jboolean setDTR);



/* read/write */

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_available
(JNIEnv *env, jobject obj, jint handle);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_pending
(JNIEnv *env, jobject obj, jint handle);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_readBytes
(JNIEnv *env, jobject obj,
jint handle, jbyteArray bytes, jint len);

JNIEXPORT jint JNICALL
Java_com_poixson_serial_natives_NativeD2xxProp_writeBytes
(JNIEnv *env, jobject obj, jint handle, jbyteArray bytes);
