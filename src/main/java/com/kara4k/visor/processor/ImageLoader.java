package com.kara4k.visor.processor;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ImageLoader {

	private final static Logger logger = Logger.getLogger(ImageLoader.class.getName());

	public static BufferedImage loadImage(final File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (final IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
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
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return image;
	}
}
