package com.kara4k.visor.main;

import com.kara4k.visor.model.Params;
import com.kara4k.visor.ui.MainFrame;
import com.kara4k.visor.util.ErrorUtils;

import javax.swing.*;

public class MainExecutor {


	public static void execute(final Params params) {
		if (params.isPixelsMode()) {
			if (params.getPixelsToGetColor() != null) {
				ImageProcessor.showPixelColors(params);
			} else if (params.getPixelsToCompare() != null) {
				ImageProcessor.comparePixels(params);
			}
		} else if (params.isScreenshotMode()) {
			FileCreator.createScreenshot(params);
		} else if (params.isInteractiveMode()) {
			try {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
				} catch (final Exception e) {
					com.kara4k.visor.util.ErrorUtils.printErrorAndExit(e::getMessage);
				}
				new MainFrame().start(params);
			} catch (final InterruptedException e) {
				ErrorUtils.printErrorAndExit(e::getMessage);
			}
		} else if (params.getPointsToCalculate() != null) {
			CoordsCalculator.calculate(params);
		} else {
			ImageProcessor.findMatchedAreas(params);
		}

	}

}
