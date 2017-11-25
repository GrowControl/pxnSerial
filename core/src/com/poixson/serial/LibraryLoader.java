package com.poixson.serial;

import java.util.concurrent.atomic.AtomicBoolean;

import com.poixson.utils.ErrorMode;
import com.poixson.utils.Keeper;
import com.poixson.utils.NativeAutoLoader;


public class LibraryLoader {

	private static volatile LibraryLoader instance = null;
	private static final Object instanceLock = new Object();

	private final NativeAutoLoader loader;

	private boolean useD2xxOpen = true;
	private boolean useD2xxProp = true;

	private final AtomicBoolean hasLoaded_D2xxOpen  = new AtomicBoolean(false);
	private final AtomicBoolean hasLoaded_D2xxProp  = new AtomicBoolean(false);
	private final AtomicBoolean hasLoaded_pxnSerial = new AtomicBoolean(false);



	public static LibraryLoader get() {
		if (instance == null) {
			synchronized (instanceLock) {
				if (instance == null) {
					instance = new LibraryLoader();
					Keeper.add(instance);
				}
			}
		}
		return instance;
	}
	private LibraryLoader() {
		this.loader =
			(new NativeAutoLoader())
				.setErrorMode(ErrorMode.EXCEPTION)
				.setClassRef(pxnSerial.class)
				.addDefaultSearchPaths()
				.setResourcesPath("lib/linux64/")
				.setLocalLibPath("lib/")
				.enableExtract()
				.enableReplace();
	}



	public boolean Load() {
		boolean result = false;
		// load libftdi.so (open)
		if (this.useD2xxOpen) {
			if (this.hasLoaded_D2xxOpen.compareAndSet(false, true)) {
				if ( ! this.loader.Load("libftdi-open-linux64.so") ) {
					throw new RuntimeException("Failed to load libftdi open library!");
				}
				result = true;
			}
		}
		// load libftd2xx.so (prop)
		if (this.useD2xxProp) {
			if (this.hasLoaded_D2xxProp.compareAndSet(false, true)) {
				if ( ! this.loader.Load("libftdi-prop-linux64.so") ) {
					throw new RuntimeException("Failed to load ftd2xx official library!");
				}
				result = true;
			}
		}
		// load pxnserial.so
		if ( ! this.loader.Load("pxnserial-linux64.so") ) {
			if (this.hasLoaded_pxnSerial.compareAndSet(false, true)) {
				throw new RuntimeException("Failed to load pxnSerial native library!");
			}
			result = true;
		}
		return result;
	}



	// use open d2xx
	public LibraryLoader useD2xxOpen() {
		return this.useD2xxOpen(true);
	}
	public LibraryLoader noD2xxOpen() {
		return this.useD2xxOpen(false);
	}
	public LibraryLoader useD2xxOpen(final boolean use) {
		this.useD2xxOpen = use;
		return this;
	}



	// use prop d2xx
	public LibraryLoader useD2xxProp() {
		return this.useD2xxProp(true);
	}
	public LibraryLoader noD2xxProp() {
		return this.useD2xxProp(false);
	}
	public LibraryLoader useD2xxProp(final boolean use) {
		this.useD2xxProp = use;
		return this;
	}



}
