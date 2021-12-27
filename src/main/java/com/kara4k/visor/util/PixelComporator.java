package com.kara4k.visor.util;

import com.kara4k.visor.model.IntPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class PixelComporator {

	public static List<Rectangle> findMatches(
			final BufferedImage source, final BufferedImage target, final Rectangle searchArea,
			final BiPredicate<Color, Color> biPredicate
	) {

		final List<Rectangle> result = new ArrayList<>();
		final int targetRGB = target.getRGB(0, 0);
		final int startY = searchArea == null ? 0 : (int) searchArea.getY();
		final int startX = searchArea == null ? 0 : (int) searchArea.getX();
		final int sourceHeight = searchArea == null ? source.getHeight() : (int) searchArea.getHeight();
		final int sourceWidth = searchArea == null ? source.getWidth() : (int) searchArea.getWidth();

		rows:
		for (int y = startY; y < sourceHeight; y++) {
			if (y + target.getHeight() > sourceHeight) {
				break;
			}

			for (int x = startX; x < sourceWidth; x++) {
				final int sourceRGB = source.getRGB(x, y);

				if (biPredicate.test(new Color(sourceRGB), new Color(targetRGB))) {
					if (target.getWidth() + x > sourceWidth) {
						continue rows;
					}

					if (isRectangleEquals(source, target, new Point(x, y), biPredicate)) {
						result.add(new Rectangle(x, y, target.getWidth(), target.getHeight()));
						x += target.getWidth();
					}
				}
			}
		}

		return result;
	}

	private static boolean isRectangleEquals(
			final BufferedImage source, final BufferedImage target, final Point sourceStart,
			final BiPredicate<Color, Color> biPredicate
	) {

		for (int y = 0; y < target.getHeight(); y++) {
			for (int x = 0; x < target.getWidth(); x++) {
				final int targetRGB = target.getRGB(x, y);
				final int sourceRGB = source.getRGB((int) sourceStart.getX() + x, (int) sourceStart.getY() + y);

				if (!biPredicate.test(new Color(sourceRGB), new Color(targetRGB))) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isPointMatches(
			final BufferedImage source, final IntPoint point, final BiPredicate<Color, Color> biPredicate
	) {

		final int actualRgb = source.getRGB(point.getX(), point.getY());
		return biPredicate.test(new Color(actualRgb), point.getColor());
	}

	public static boolean isPointsMatches(
			final BufferedImage source, final IntPoint[] points, final BiPredicate<Color, Color> biPredicate
	) {
		for (final IntPoint point: points) {
			final int actualRgb = source.getRGB(point.getX(), point.getY());
			final boolean matches = biPredicate.test(new Color(actualRgb), point.getColor());
			if (!matches) {
				return false;
			}
		}
		return true;
	}
}