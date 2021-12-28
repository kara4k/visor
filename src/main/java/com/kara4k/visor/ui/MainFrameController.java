package com.kara4k.visor.ui;

import com.kara4k.visor.main.FileCreator;
import com.kara4k.visor.main.ResultPrinter;
import com.kara4k.visor.main.RobotWrapper;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.util.ErrorUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class MainFrameController {

	public static final long DELAY = 50;

	private final Params params;
	private final Callback callback;
	private File file;
	private Point point;
	private Color color;
	private String frostPoint;

	interface Callback {

		void showWindow(String filename);

		void update(Point point, Color color);

		void showError(String message);
	}

	public MainFrameController(final Params params, final Callback callback) {
		this.params = params;
		this.callback = callback;
	}

	public void run() throws InterruptedException {
		file = FileCreator.defineOutputFile(params, "coords", ".txt");
		callback.showWindow(file.getAbsolutePath());

		while (true) {
			point = MouseInfo.getPointerInfo().getLocation();
			color = RobotWrapper.getInstance().getPixelColor(point);
			callback.update(point, color);
			Thread.sleep(DELAY);
		}
	}

	public void appendPoint() {
		final String coords;
		if (params.getOutputPattern() != null) {
			coords = String.format(params.getOutputPattern(), (int) point.getX(), (int) point.getY(), color.getRed(),
								   color.getGreen(), color.getBlue(), file.getAbsolutePath());
		} else {
			coords = ResultPrinter.createDelimitedString(params.getDelimiter(), point, color);
		}
		appendToFile(coords);
	}

	public void appendText(final String text) {
		appendToFile(text);
	}

	public String freezePoint() {
		frostPoint = ResultPrinter.createDelimitedString(params.getDelimiter(), point, color);
		return ResultPrinter.createUiFormatString(point, color);
	}

	public void appendFrostPoint(final String description) {
		appendToFile(frostPoint + params.getDelimiter() + description);
	}

	private void appendToFile(final String line) {
		try {
			Files.write(file.toPath(), (line + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (final IOException e) {
			callback.showError(e.getMessage());
			ErrorUtils.printErrorAndExit(e::getMessage);
		}
	}

}
