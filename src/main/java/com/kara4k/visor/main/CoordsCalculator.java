package com.kara4k.visor.main;

import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.model.Params;

public class CoordsCalculator {

	public static void calculate(final Params params) {
		final IntPoint[] pointsToCalculate = params.getPointsToCalculate();
		for (int i = 0; i < pointsToCalculate.length; i += 2) {
			final IntPoint leftUpper = pointsToCalculate[i];
			final IntPoint rightLower = pointsToCalculate[i + 1];
			ResultPrinter.printWidthHeight(params, leftUpper, rightLower);
		}
	}

}
