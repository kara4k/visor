package com.kara4k.visor.main;

import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.util.CoordUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.BiPredicate;

public class ImageProcessor {

	public static void findMatchedAreas(final Params params) {
		final BufferedImage sourceImage = ImageLoader.loadSourceImage(params.getSourceImage(), params.getDelay());
		final File[] targetImages = params.getTargetImages();

		if (targetImages == null) {
			return;
		}

		final BiPredicate<Color, Color> rgbBiPredicate = createRgbBiPredicate(params.getAccuracy());
		final Rectangle searchArea =
				CoordUtils.createRectangle(sourceImage.getWidth(), sourceImage.getHeight(), params.getRectangle());

		for (int i = 0; i < targetImages.length; i++) {
			final File tImage = targetImages[i];
			final BufferedImage image = ImageLoader.loadImage(tImage);
			final List<Rectangle> matches = PixelComparator.findMatches(sourceImage, image, searchArea, rgbBiPredicate);
			ResultPrinter.printFoundTargets(params, matches, tImage, i == targetImages.length - 1);
		}
	}

	public static void comparePixels(final Params params) {
		final BufferedImage sourceImage = ImageLoader.loadSourceImage(params.getSourceImage(), params.getDelay());
		final IntPoint[] pixelsToCompare = params.getPixelsToCompare();
		final BiPredicate<Color, Color> rgbBiPredicate = createRgbBiPredicate(params.getAccuracy());

		if (params.isShowEveryPixelMatch()) {
			for (final IntPoint point: pixelsToCompare) {
				final boolean pointMatches = PixelComparator.isPointMatches(sourceImage, point, rgbBiPredicate);
				ResultPrinter.printTestResult(params, point, pointMatches);
			}
		} else {
			final boolean pointsMatches = PixelComparator.isPointsMatches(sourceImage, pixelsToCompare, rgbBiPredicate);
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
			final Rectangle rectangle =
					CoordUtils.createRectangle(sourceImage.getWidth(), sourceImage.getHeight(), params.getRectangle());
			for (int i = (int) rectangle.getY(); i < rectangle.getY() + rectangle.getHeight(); i++) {
				for (int j = (int) rectangle.getX(); j < rectangle.getX() + rectangle.getWidth(); j++) {
					final int rgb = sourceImage.getRGB(j, i);
					ResultPrinter.printPixelColor(params, new IntPoint(j, i, new Color(rgb)));
				}
			}
		}
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
