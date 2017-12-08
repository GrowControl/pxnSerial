package com.poixson.serial;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.utils.ErrorMode;
import com.poixson.utils.NativeAutoLoader;


public class SerialLibraryLoader {

	private static final AtomicReference<SerialLibraryLoader> instance =
			new AtomicReference<SerialLibraryLoader>(null);

	protected final NativeAutoLoader autoLoader;



	public static SerialLibraryLoader get() {
		{
			final SerialLibraryLoader loader = instance.get();
			if (loader != null)
				return loader;
		}
		{
			final SerialLibraryLoader loader = new SerialLibraryLoader();
			if (!instance.compareAndSet(null, loader)) {
				return instance.get();
			}
			return loader;
		}
	}
	protected SerialLibraryLoader() {
		this.autoLoader = NativeAutoLoader.getNew()
			.addDefaultSearchPaths()
			.setLocalLibPath("lib")
			.setResourcesPath("lib/linux64")
			.enableExtract()
			.enableReplace()
			.setClassRef(SerialLibraryLoader.class)
			.setErrorMode(ErrorMode.EXCEPTION);
	}



	public NativeAutoLoader getAutoLoader() {
		return this.autoLoader.clone();
	}



	// load serial library
	public void loadSerialLibrary() {
		this.loadSerialLibrary(
			this.getAutoLoader()
		);
	}
	public void loadSerialLibrary(final NativeAutoLoader loader) {
		loader
			.setFileNameIfNull("pxnserial-linux64.so")
			.load();
	}



	// load ftdi open library
	public void loadD2xxOpenLibrary() {
		this.loadD2xxOpenLibrary(
			this.getAutoLoader()
		);
	}
	public void loadD2xxOpenLibrary(final NativeAutoLoader loader) {
		this.getAutoLoader()
			.setFileNameIfNull("libftdi-open-linux64.so")
			.load();
	}



	// load ftdi prop library
	public void loadD2xxPropLibrary() {
		this.loadD2xxPropLibrary(
			this.getAutoLoader()
		);
	}
	public void loadD2xxPropLibrary(final NativeAutoLoader loader) {
		this.getAutoLoader()
			.setFileNameIfNull("libftdi-prop-linux64.so")
			.load();
	}



}
