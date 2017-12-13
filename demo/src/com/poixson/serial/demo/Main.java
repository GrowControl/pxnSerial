package com.poixson.serial.demo;

import com.poixson.utils.Keeper;
import com.poixson.utils.NativeAutoLoader;
import com.poixson.utils.NativeAutoLoader.AutoMode;


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
			final NativeAutoLoader loader =
					NativeAutoLoader.getNew(AutoMode.AUTO_MODE_SELF_CONTAINED)
						.setDefaults(Main.class)
						.setLocalLibPath("lib")
						.setResourcesPath("lib/linux64");
				// load d2xx open library
				loader.clone()
					.load("libftdi-open-linux64.so");
				// load d2xx prop library
				loader.clone()
					.load("libftdi-prop-linux64.so");
				// load serial library
				loader.clone()
					.load("pxnserial-linux64.so");
		}
	}



}
