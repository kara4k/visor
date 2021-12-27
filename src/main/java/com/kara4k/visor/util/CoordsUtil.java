package com.kara4k.visor.util;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CoordsUtil {

	public static Point getCenterPoint(@NotNull final Rectangle rectangle) {
		final int centerX = (int) (rectangle.getX() + rectangle.getWidth() / 2);
		final int centerY = (int) (rectangle.getY() + rectangle.getHeight() / 2);
		return new Point(centerX, centerY);
	}

}
