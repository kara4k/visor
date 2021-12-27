package com.kara4k.visor.util;

import java.util.function.Supplier;

public class ErrorUtil {

	public static void printErrorAndExit(final Supplier<String> message) {
		System.err.println(message.get());
		System.exit(1);
	}
}
