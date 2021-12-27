package com.kara4k.visor.util;

import com.kara4k.visor.model.IntPoint;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Function;

public class ArgsConverter {

	public static IntPoint[] convertPixelsToGetColor(@NotNull final String[] pixelsToGetColor) {
		return convertToIntPoints(pixelsToGetColor, 2, new Function<String[], IntPoint>() {
			@Override
			public IntPoint apply(final String[] split) {
				final int x = Integer.parseInt(split[0]);
				final int y = Integer.parseInt(split[1]);
				return new IntPoint(x, y);
			}
		});
	}

	public static IntPoint[] convertPixelsToCompare(final String[] pixelsToCompare) {
		return convertToIntPoints(pixelsToCompare, 5, split -> {
			final int x = Integer.parseInt(split[0]);
			final int y = Integer.parseInt(split[1]);
			final int r = Integer.parseInt(split[2]);
			final int g = Integer.parseInt(split[3]);
			final int b = Integer.parseInt(split[4]);
			return new IntPoint(x, y, new Color(r, g, b));
		});
	}

	public static IntPoint[] convertToIntPoints(
			final String[] pixelsToCompare, final int forceSplitCount, final Function<String[], IntPoint> converter
	) {
		final IntPoint[] points = new IntPoint[pixelsToCompare.length];
		for (int i = 0; i < pixelsToCompare.length; i++) {
			final String arg = pixelsToCompare[i];
			final String[] split = arg.split(",");
			if (split.length != forceSplitCount) {
				ErrorUtil.printErrorAndExit(() -> "Wrong argument: " + arg);
			}
			try {
				final IntPoint point = converter.apply(split);
				points[i] = point;
			} catch (final NumberFormatException e) {
				ErrorUtil.printErrorAndExit(e::getMessage);
			}
		}
		return points;
	}
}
