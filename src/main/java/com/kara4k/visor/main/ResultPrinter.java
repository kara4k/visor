package com.kara4k.visor.main;

import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.model.OutputMode;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.util.CoordUtils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.function.Supplier;

public class ResultPrinter {

	public static void printFoundTargets(
			@NotNull final Params params, @NotNull final List<Rectangle> rectangles, @NotNull final File targetFile,
			final boolean lastTarget

	) {
		if (params.getOutputMode() == OutputMode.NAME) {
			System.out.println(targetFile.getName());
		}

		if (params.getOutputMode() == OutputMode.PATH) {
			System.out.println(targetFile.getAbsolutePath());
		}

		for (final Rectangle rectangle: rectangles) {
			if (params.isOutputMatterOnly()) {
				final IntPoint centerPoint = CoordUtils.getCenterPoint(rectangle);
				final String line =
						createDelimitedString(params.getDelimiter(), centerPoint.getX(), centerPoint.getY());
				System.out.println(line);

			} else if (params.getOutputPattern() != null && !params.getOutputPattern().isEmpty()) {
				final IntPoint centerPoint = CoordUtils.getCenterPoint(rectangle);
				final String formattedLine =
						String.format(params.getOutputPattern(), (int) rectangle.getX(), (int) rectangle.getY(),
									  (int) rectangle.getWidth(), (int) rectangle.getHeight(), centerPoint.getX(),
									  centerPoint.getY(), targetFile.getName(), targetFile.getAbsolutePath());
				System.out.println(formattedLine);
			} else {
				final String delimitedString =
						createDelimitedString(params.getDelimiter(), (int) rectangle.getX(), (int) rectangle.getY(),

											  (int) rectangle.getWidth(), (int) rectangle.getHeight());
				System.out.println(delimitedString);
			}
		}

		if (params.getOutputMode() == OutputMode.SPACE && !lastTarget) {
			System.out.println();
		}
	}

	public static void printPixelColor(final Params params, final IntPoint intPoint) {

		if (params.getOutputPattern() != null && !params.getOutputPattern().isEmpty()) {
			final String sourceName = params.getSourceImage() == null ? "" : params.getSourceImage().getAbsolutePath();
			final String line = String.format(params.getOutputPattern(), intPoint.getX(), intPoint.getY(),
											  intPoint.getColor().getRed(), intPoint.getColor().getGreen(),
											  intPoint.getColor().getBlue(), sourceName);
			System.out.println(line);
		} else if (params.isOutputMatterOnly()) { // todo rename
			final String line = createDelimitedString(params.getDelimiter(), intPoint.getColor().getRed(),
													  intPoint.getColor().getGreen(), intPoint.getColor().getBlue());
			System.out.println(line);
		} else {
			final String line = createDelimitedString(params.getDelimiter(), intPoint.getX(), intPoint.getY(),
													  intPoint.getColor().getRed(), intPoint.getColor().getGreen(),
													  intPoint.getColor().getBlue());
			System.out.println(line);
		}
	}

	private static String createDelimitedString(final String delimiter, final int... coords) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < coords.length; i++) {
			sb.append(coords[i]);
			if (i != coords.length - 1) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

	public static void printTestResult(final Params params, final IntPoint point, final boolean success) {
		final String line = createDelimitedString(params.getDelimiter(), point.getX(), point.getY(), success ? 0 : 1);
		System.out.println(line);
	}

	public static void printTestResult(final boolean success) {
		System.out.println(success ? 0 : 1);
	}

	public static void print(final Supplier<String> message) {
		System.out.println(message.get());
	}

}
