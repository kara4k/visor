package com.kara4k.visor.main;

import com.kara4k.visor.util.ErrorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class RobotWrapper {

	private final static Logger logger = Logger.getLogger(RobotWrapper.class.getName());

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
		logger.info("Attempt to create full screenshot");
		return robot.createScreenCapture(getFullScreenRectangle());
	}

	public BufferedImage getScreenshot(final Rectangle rectangle) {
		logger.info("Attempt to create screenshot with " + rectangle);
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
