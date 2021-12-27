package com.kara4k.visor.processor;

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
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public static RobotWrapper getInstance() {
		if (robotWrapper == null) {
			robotWrapper = new RobotWrapper();
		}
		return robotWrapper;
	}

	public BufferedImage getFullScreenshot() {
		logger.info("Attempt to create fullscreen image");
		return robot.createScreenCapture(getFullScreenRectangle());
	}

	//	public BufferedImage getScreenshot(final int[] points) {
	//		final Rectangle rectangle = createRectangle(points);
	//		return robot.createScreenCapture(rectangle);
	//	}

	public Rectangle getFullScreenRectangle() {

		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
	}

	//	@NotNull
	//	private Rectangle createRectangle(final int[] points) {
	//		final Rectangle rectangle = new Rectangle();
	//
	//		if (points != null && points.length >= 4) {
	//			rectangle.setRect(points[0], points[1], points[2], points[3]);
	//			return rectangle;
	//		}
	//
	//		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//
	//		if (points == null) {
	//			return getFullScreenRectangle();
	//		}
	//
	//		switch (points.length) {
	//			case 1:
	//				rectangle.setRect(points[0], 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
	//				return rectangle;
	//			case 2:
	//				rectangle.setRect(points[0], points[1], (int) screenSize.getWidth(), (int) screenSize.getHeight());
	//				return rectangle;
	//			case 3:
	//				rectangle.setRect(points[0], points[1], points[2], (int) screenSize.getHeight());
	//				return rectangle;
	//		}
	//
	//		return rectangle;
	//	}
}
