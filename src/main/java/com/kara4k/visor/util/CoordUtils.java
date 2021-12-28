package com.kara4k.visor.util;

import com.kara4k.visor.model.IntPoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class CoordUtils {

	public static IntPoint getCenterPoint(@NotNull final Rectangle rectangle) {
		final int centerX = (int) (rectangle.getX() + rectangle.getWidth() / 2);
		final int centerY = (int) (rectangle.getY() + rectangle.getHeight() / 2);
		return new IntPoint(centerX, centerY);
	}

	public static IntPoint getCenterPoint(@NotNull final IntPoint leftUpper, @NotNull final IntPoint rightLower) {
		final int centerX = (rightLower.getX() - leftUpper.getX()) / 2 + leftUpper.getX();
		final int centerY = (rightLower.getY() - leftUpper.getY()) / 2 + leftUpper.getY();
		return new IntPoint(centerX, centerY);
	}

	@NotNull
	public static Rectangle createRectangle(
			final int width, final int height, @Nullable final int[] points
	) {

		if (points != null && points.length >= 4) {
			return new Rectangle(points[0], points[1], points[2], points[3]);
		}
		if (points == null) {
			return new Rectangle(0, 0, width, height);
		}

		final Rectangle rectangle = new Rectangle();

		switch (points.length) {
			case 1:
				rectangle.setRect(points[0], 0, width - points[0], height);
				return rectangle;
			case 2:
				rectangle.setRect(points[0], points[1], width - points[0], height - points[1]);
				return rectangle;
			case 3:
				rectangle.setRect(points[0], points[1], points[2], height - points[1]);
				return rectangle;
		}
		return rectangle;
	}

	public static IntPoint getWidthHeight(final IntPoint leftUpper, final IntPoint rightLower) {
		final int width = rightLower.getX() - leftUpper.getX();
		final int height = rightLower.getY() - leftUpper.getY();
		return new IntPoint(width, height);
	}
}
