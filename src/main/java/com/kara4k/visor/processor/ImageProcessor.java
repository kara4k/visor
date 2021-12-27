package com.kara4k.visor.processor;

import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.util.PixelComporator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

public class ImageProcessor {

	private final static Logger logger = Logger.getLogger(ImageProcessor.class.getName());

	public static void findMatchedAreas(final Params params) {
		final BufferedImage sourceImage = ImageLoader.loadSourceImage(params.getSourceImage(), params.getDelay());
		final File[] targetImages = params.getTargetImages();

		logger.info("Target images: " + Arrays.toString(targetImages));

		if (targetImages == null) {
			return;
		}

		final BiPredicate<Color, Color> rgbBiPredicate = createRgbBiPredicate(params.getAccuracy());
		final Rectangle searchArea = createRectangle(sourceImage, params.getRectangle());

		logger.info("Search area: " + searchArea);

		for (int i = 0; i < targetImages.length; i++) {
			final File tImage = targetImages[i];
			final BufferedImage image = ImageLoader.loadImage(tImage);
			final List<Rectangle> matches = PixelComporator.findMatches(sourceImage, image, searchArea, rgbBiPredicate);
			ResultPrinter.printFoundTargets(params, matches, tImage, i != targetImages.length - 1);
		}
	}

	public static void comparePixels(final Params params) {
		final BufferedImage sourceImage = ImageLoader.loadSourceImage(params.getSourceImage(), params.getDelay());
		final IntPoint[] pixelsToCompare = params.getPixelsToCompare();
		final BiPredicate<Color, Color> rgbBiPredicate = createRgbBiPredicate(params.getAccuracy());
		if (params.isShowEveryPixelMatch()) {
			for (final IntPoint point: pixelsToCompare) {
				final boolean pointMatches = PixelComporator.isPointMatches(sourceImage, point, rgbBiPredicate);
				ResultPrinter.printTestResult(params, point, pointMatches);
			}
		} else {
			final boolean pointsMatches = PixelComporator.isPointsMatches(sourceImage, pixelsToCompare, rgbBiPredicate);
			ResultPrinter.printTestResult(pointsMatches);
		}
	}

	public static void showPixelColors(final Params params) {
		final BufferedImage sourceImage = ImageLoader.loadSourceImage(params.getSourceImage(), params.getDelay());
		final IntPoint[] pixelsToGetColor = params.getPixelsToGetColor();
		for (final IntPoint intPoint: pixelsToGetColor) {
			final int rgb = sourceImage.getRGB(intPoint.getX(), intPoint.getY());
			intPoint.setColor(new Color(rgb));
			ResultPrinter.printPixelColor(params, intPoint);
		}
		if (params.getRectangle() != null) {
			final Rectangle rectangle = createRectangle(sourceImage, params.getRectangle());
			for (int i = (int) rectangle.getY(); i < rectangle.getY() + rectangle.getHeight(); i++) {
				for (int j = (int) rectangle.getX(); j < rectangle.getX() + rectangle.getWidth(); j++) {
					final int rgb = sourceImage.getRGB(j, i);
					ResultPrinter.printPixelColor(params, new IntPoint(j, i, new Color(rgb)));
				}
			}
		}
	}

	@NotNull
	private static Rectangle createRectangle(final BufferedImage image, @Nullable final int[] points) {
		final Rectangle rectangle = new Rectangle();

		if (points != null && points.length >= 4) {
			rectangle.setRect(points[0], points[1], points[2], points[3]);
			return rectangle;
		}

		if (points == null) {
			rectangle.setRect(0, 0, image.getWidth(), image.getHeight());
			return rectangle;
		}

		switch (points.length) {
			case 1:
				rectangle.setRect(points[0], 0, image.getWidth(), image.getHeight());
				return rectangle;
			case 2:
				rectangle.setRect(points[0], points[1], image.getWidth(), image.getHeight());
				return rectangle;
			case 3:
				rectangle.setRect(points[0], points[1], points[2], image.getHeight());
				return rectangle;
		}

		return rectangle;
	}

	private static BiPredicate<Color, Color> createRgbBiPredicate(final int[] colorsDiff) {
		final int[] rgb = new int[] {0, 0, 0};

		if (colorsDiff != null) {
			for (int i = 0; i < colorsDiff.length; i++) {
				if (i > 2) {
					break;
				}
				rgb[i] = colorsDiff[i];
			}
		}

		return (color, color2) -> {
			final boolean red = Math.abs(color.getRed() - color2.getRed()) <= rgb[0];
			final boolean green = Math.abs(color.getGreen() - color2.getGreen()) <= rgb[1];
			final boolean blue = Math.abs(color.getBlue() - color2.getBlue()) <= rgb[2];
			return red && green && blue;
		};
	}
}
