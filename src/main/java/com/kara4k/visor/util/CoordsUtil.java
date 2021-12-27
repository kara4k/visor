package com.kara4k.visor.util;

import com.kara4k.visor.model.IntPoint;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CoordsUtil {

	public static IntPoint getCenterPoint(@NotNull final Rectangle rectangle) {
		final int centerX = (int) (rectangle.getX() + rectangle.getWidth() / 2);
		final int centerY = (int) (rectangle.getY() + rectangle.getHeight() / 2);
		return new IntPoint(centerX, centerY);
	}

}
