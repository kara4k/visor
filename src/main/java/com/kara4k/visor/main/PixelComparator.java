package com.kara4k.visor.main;

import com.kara4k.visor.model.IntPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class PixelComparator {

	public static List<Rectangle> findMatches(
			final BufferedImage source, final BufferedImage target, final Rectangle searchArea,
			final BiPredicate<Color, Color> biPredicate
	) {

		final List<Rectangle> result = new ArrayList<>();
		final int targetRGB = target.getRGB(0, 0);
		final int startY = searchArea == null ? 0 : (int) searchArea.getY();
		final int startX = searchArea == null ? 0 : (int) searchArea.getX();
		final int rectangleWidth = searchArea == null ? source.getHeight() : (int) searchArea.getWidth() + startX;
		final int rectangleHeight = searchArea == null ? source.getWidth() : (int) searchArea.getHeight() + startY;

		rows:
		for (int y = startY; y < rectangleHeight; y++) {
			if (y + target.getHeight() > source.getHeight()) {
				break;
			}

			for (int x = startX; x < rectangleWidth; x++) {
				final int sourceRGB = source.getRGB(x, y);

				if (biPredicate.test(new Color(sourceRGB), new Color(targetRGB))) {
					if (target.getWidth() + x > source.getWidth()) {
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