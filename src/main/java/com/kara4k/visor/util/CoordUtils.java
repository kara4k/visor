package com.kara4k.visor.util;

import com.kara4k.visor.model.IntPoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.logging.Logger;

public class CoordUtils {

	private final static Logger logger = Logger.getLogger(CoordUtils.class.getName());

	public static IntPoint getCenterPoint(@NotNull final Rectangle rectangle) {
		final int centerX = (int) (rectangle.getX() + rectangle.getWidth() / 2);
		final int centerY = (int) (rectangle.getY() + rectangle.getHeight() / 2);
		return new IntPoint(centerX, centerY);
	}

	@NotNull
	public static Rectangle createRectangle(
			final BufferedImage source, @Nullable final int[] points
	) {
		Rectangle rectangle = attemptCreateRectangle(source.getWidth(), source.getHeight(), points);

		if (rectangle != null) {
			return rectangle;
		}

		rectangle = new Rectangle();

		switch (points.length) {
			case 1:
				rectangle.setRect(points[0], 0, source.getWidth(), source.getHeight());
				logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
				return rectangle;
			case 2:
				rectangle.setRect(points[0], points[1], source.getWidth(), source.getHeight());
				logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
				return rectangle;
			case 3:
				rectangle.setRect(points[0], points[1], points[2], source.getHeight());
				logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
				return rectangle;
		}

		return rectangle;
	}

	@NotNull
	public static Rectangle createRectangle(
			final Dimension source, @Nullable final int[] points
	) {
		Rectangle rectangle = attemptCreateRectangle((int) source.getWidth(), (int) source.getHeight(), points);

		if (rectangle != null) {
			return rectangle;
		}

		rectangle = new Rectangle();

		switch (points.length) {
			case 1:
				rectangle.setRect(points[0], 0, source.getWidth() - points[0], source.getHeight());
				logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
				return rectangle;
			case 2:
				rectangle.setRect(points[0], points[1], source.getWidth() - points[0], source.getHeight() - points[1]);
				logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
				return rectangle;
			case 3:
				rectangle.setRect(points[0], points[1], points[2], source.getHeight() - points[1]);
				logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
				return rectangle;
		}

		return rectangle;
	}

	@Nullable
	private static Rectangle attemptCreateRectangle(final int width, final int height, final int[] points) {

		if (points != null && points.length >= 4) {
			final Rectangle rectangle = new Rectangle(points[0], points[1], points[2], points[3]);
			logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
			return rectangle;
		}

		if (points == null) {
			final Rectangle rectangle = new Rectangle(0, 0, width, height);
			logger.info("Points: " + Arrays.toString(points) + " Rectangle: " + rectangle.toString());
			return rectangle;
		}
		return null;
	}

}
