package com.kara4k.visor.processor;

import com.kara4k.visor.model.Params;

import java.util.logging.Logger;

public class MainExecutor {

	private final static Logger logger = Logger.getLogger(MainExecutor.class.getName());

	public void execute(final Params params) {
		ImageSearchProcessor.findMatchedAreas(params);
	}

}
