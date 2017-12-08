package com.poixson.serial.demo;

import com.poixson.nettet.tool.Main;
import com.poixson.serial.SerialLibraryLoader;
import com.poixson.utils.ErrorMode;
import com.poixson.utils.Keeper;
import com.poixson.utils.NativeAutoLoader;
import com.poixson.utils.ShellArgsTool;


public class Main {

	// keep things in memory
	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();



	public static void main(final String[] argsArray) {
//TODO:
//		// process shell arguments
//		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
		// load libraries
		{
			final SerialLibraryLoader loader =
				SerialLibraryLoader.get();
			// load serial library
			loader.loadSerialLibrary();
			// load d2xx open library
			loader.loadD2xxOpenLibrary();
			// load d2xx prop library
			loader.loadD2xxPropLibrary();
		}
	}



}
