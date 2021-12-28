package com.kara4k.visor.main;

import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.util.ErrorUtils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Function;

public class ArgsConverter {

	public static IntPoint[] convertPixelsToGetColor(
			final String delimiter, @NotNull final String[] pixelsToGetColor
	) {
		return convertPixels(delimiter, pixelsToGetColor, 2, split -> {
			final int x = Integer.parseInt(split[0]);
			final int y = Integer.parseInt(split[1]);
			return new IntPoint(x, y);
		});
	}

	public static IntPoint[] convertPixelsToCompare(final String delimiter, final String[] pixelsToCompare) {
		return convertPixels(delimiter, pixelsToCompare, 5, split -> {
			final int x = Integer.parseInt(split[0]);
			final int y = Integer.parseInt(split[1]);
			final int r = Integer.parseInt(split[2]);
			final int g = Integer.parseInt(split[3]);
			final int b = Integer.parseInt(split[4]);
			return new IntPoint(x, y, new Color(r, g, b));
		});
	}

	public static IntPoint[] convertPixels(
			final String delimiter, final String[] pixelsToCompare, final int forceSplitCount,
			final Function<String[], IntPoint> converter
	) {
		final IntPoint[] points = new IntPoint[pixelsToCompare.length];
		for (int i = 0; i < pixelsToCompare.length; i++) {
			final String arg = pixelsToCompare[i];
			final String[] split = arg.split(delimiter);
			if (split.length != forceSplitCount) {
				ErrorUtils.printErrorAndExit(() -> "Wrong argument: " + arg);
			}
			try {
				final IntPoint point = converter.apply(split);
				points[i] = point;
			} catch (final NumberFormatException e) {
				ErrorUtils.printErrorAndExit(e::getMessage);
			}
		}
		return points;
	}

	public static IntPoint[] convertPointsToCalculate(
			final String delimiter, final String[] pointsToCalculate
	) {
		if (pointsToCalculate.length % 2 != 0) {
			ErrorUtils.printErrorAndExit(
					() -> "Wrong argument: " + Arrays.toString(pointsToCalculate) + ". Must be a multiple of two");
		}
		final IntPoint[] points = new IntPoint[pointsToCalculate.length];
		for (int i = 0; i < pointsToCalculate.length; i++) {
			final String[] split = pointsToCalculate[i].split(delimiter);
			final int x = Integer.parseInt(split[0]);
			final int y = Integer.parseInt(split[1]);
			points[i] = new IntPoint(x, y);
		}
		return points;
	}
}
