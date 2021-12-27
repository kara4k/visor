package com.kara4k.visor.processor;

import com.kara4k.visor.util.ErrorUtil;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ImageLoader {

	private final static Logger logger = Logger.getLogger(ImageLoader.class.getName());

	public static BufferedImage loadSourceImage(final File file, final long delay) {
		BufferedImage sourceImage = null;
		if (file == null) {
			try {
				logger.info("Source is null, start sleep for " + delay + "before screenshot capturing");
				Thread.sleep(delay);
			} catch (final InterruptedException e) {
				ErrorUtil.printErrorAndExit(e::getMessage);
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
			ErrorUtil.printErrorAndExit(e::getMessage);
		}
		if (image == null) {
			ErrorUtil.printErrorAndExit(() -> "Can't load image from " + file.getAbsolutePath());
		}
		return image;
	}

	public static BufferedImage loadImage(final File file, @NotNull final Rectangle rectangle) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			logger.info("Attempt to subimage: " + rectangle);
			image = image.getSubimage((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
									  (int) rectangle.getHeight());
		} catch (final IOException e) {
			ErrorUtil.printErrorAndExit(e::getMessage);
		}
		return image;
	}
}
