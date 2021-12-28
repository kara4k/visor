package com.kara4k.visor.main;

import com.kara4k.visor.util.ErrorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RobotWrapper {


	private static RobotWrapper robotWrapper = null;
	private Robot robot;

	private RobotWrapper() {
		try {
			this.robot = new Robot();
		} catch (final AWTException e) {
			ErrorUtils.printErrorAndExit(e::getMessage);
		}
	}

	public static RobotWrapper getInstance() {
		if (robotWrapper == null) {
			robotWrapper = new RobotWrapper();
		}
		return robotWrapper;
	}

	public BufferedImage getFullScreenshot() {
		return robot.createScreenCapture(getFullScreenRectangle());
	}

	public BufferedImage getScreenshot(final Rectangle rectangle) {
		return robot.createScreenCapture(rectangle);
	}

	public Rectangle getFullScreenRectangle() {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
	}

	public Color getPixelColor(final Point point) {
		return robot.getPixelColor((int) point.getX(), (int) point.getY());
	}

}
