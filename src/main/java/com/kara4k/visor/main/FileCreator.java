package com.kara4k.visor.main;

import com.kara4k.visor.model.Params;
import com.kara4k.visor.util.CoordUtils;
import com.kara4k.visor.util.ErrorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class FileCreator {

	public static void createScreenshot(final Params params) {
		try {
			final File outputFile = defineOutputFile(params, "screenshot", ".jpg");
			final BufferedImage screenshot;

			if (params.getDelay() > 0) {
				Thread.sleep(params.getDelay());
			}

			if (params.getRectangle() != null) {
				final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final Rectangle rectangle = CoordUtils.createRectangle(screenSize, params.getRectangle());
				screenshot = RobotWrapper.getInstance().getScreenshot(rectangle);
			} else {
				screenshot = RobotWrapper.getInstance().getFullScreenshot();
			}

			ImageIO.write(screenshot, "jpg", outputFile);
			ResultPrinter.print(outputFile::getAbsolutePath);
		} catch (final Exception e) {
			ErrorUtils.printErrorAndExit(e::getMessage);
		}
	}

	public static File defineOutputFile(final Params params, final String filename, final String extension) {
		final File outputFile = params.getOutputFile();
		if (outputFile != null) {
			if (outputFile.exists()) {
				ErrorUtils.printErrorAndExit(
						() -> "File " + outputFile.getAbsolutePath() + " already exists, choose another file");
			}
			return outputFile;
		} else {
			final String workingDir = System.getProperty("user.dir");
			return new File(workingDir + "/" + filename + "_" + System.currentTimeMillis() + extension);
		}
	}
}
