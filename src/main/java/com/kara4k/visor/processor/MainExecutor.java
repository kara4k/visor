package com.kara4k.visor.processor;

import com.kara4k.visor.model.Params;

import java.util.logging.Logger;

public class MainExecutor {

	private final static Logger logger = Logger.getLogger(MainExecutor.class.getName());

	public void execute(final Params params) {
		if (params.isPixelsMode()) {
			if (params.getPixelsToGetColor() != null) {
				ImageProcessor.showPixelColors(params);
			} else if (params.getPixelsToCompare() != null) {
				ImageProcessor.comparePixels(params);
			}
		} else {
			ImageProcessor.findMatchedAreas(params);
		}

	}

}
