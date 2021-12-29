package com.kara4k.visor.main;

import com.kara4k.visor.util.ErrorUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	// TODO: 12/29/21 add url support?
	public static BufferedImage loadSourceImage(final File file, final long delay) {
		BufferedImage sourceImage = null;
		if (file == null) {
			try {
				Thread.sleep(delay);
			} catch (final InterruptedException e) {
				ErrorUtils.printErrorAndExit(e::getMessage);
			}
			sourceImage = RobotWrapper.getInstance().getFullScreenshot();
		} else {
			return ImageLoader.loadImage(file);
		}
		return sourceImage;
	}

	public static BufferedImage loadImage(final File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (final IOException e) {
			ErrorUtils.printErrorAndExit(e::getMessage);
		}
		if (image == null) {
			ErrorUtils.printErrorAndExit(() -> "Can't load image from " + file.getAbsolutePath());
		}
		return image;
	}
}
