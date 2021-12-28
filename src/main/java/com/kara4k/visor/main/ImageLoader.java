package com.kara4k.visor.main;

import com.kara4k.visor.util.ErrorUtils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

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

	public static BufferedImage loadImage(final File file, @NotNull final Rectangle rectangle) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			image = image.getSubimage((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
									  (int) rectangle.getHeight());
		} catch (final IOException e) {
			ErrorUtils.printErrorAndExit(e::getMessage);
		}
		return image;
	}
}
