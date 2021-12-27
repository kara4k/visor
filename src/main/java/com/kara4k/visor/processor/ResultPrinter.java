package com.kara4k.visor.processor;

import com.kara4k.visor.model.OutputMode;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.util.CoordsUtil;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.util.List;

public class ResultPrinter {   //// TODO: 12/26/21 own point and own Rectangle

	public static void print(
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
			if (params.isOutputCenterOnly()) {
				final Point centerPoint = CoordsUtil.getCenterPoint(rectangle);
				final String line = createDelimitedString(params.getDelimiter(), (int) centerPoint.getX(),
														  (int) centerPoint.getY());
				System.out.println(line);

			} else if (params.getOutputPattern() != null && !params.getOutputPattern().isEmpty()) {
				final Point centerPoint = CoordsUtil.getCenterPoint(rectangle);
				final String formattedLine =
						String.format(params.getOutputPattern(), (int) rectangle.getX(), (int) rectangle.getY(),
									  (int) rectangle.getWidth(), (int) rectangle.getHeight(), (int) centerPoint.getX(),
									  (int) centerPoint.getY(),targetFile.getName(), targetFile.getAbsolutePath());
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

}
