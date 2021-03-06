package com.kara4k.visor.model;

import java.io.File;
import java.util.Arrays;

public class Params {

	private File sourceImage;
	private File[] targetImages;
	private int[] rectangle;
	private int[] accuracy;
	private long delay;
	private boolean isOutputMatterOnly;
	private OutputMode outputMode;
	private String delimiter;
	private String outputPattern;

	private boolean pixelsMode;
	private IntPoint[] pixelsToGetColor;
	private IntPoint[] pixelsToCompare;
	private boolean showEveryPixelMatch;

	private boolean screenshotMode;
	private File outputFile;

	private boolean interactiveMode;

	private IntPoint[] pointsToCalculate;

	public File getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(final File sourceImage) {
		this.sourceImage = sourceImage;
	}

	public File[] getTargetImages() {
		return targetImages;
	}

	public void setTargetImages(final File[] targetImages) {
		this.targetImages = targetImages;
	}

	public int[] getRectangle() {
		return rectangle;
	}

	public void setRectangle(final int[] rectangle) {
		this.rectangle = rectangle;
	}

	public int[] getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(final int[] accuracy) {
		this.accuracy = accuracy;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(final long delay) {
		this.delay = delay;
	}

	public boolean isOutputMatterOnly() {
		return isOutputMatterOnly;
	}

	public void setOutputMatterOnly(final boolean outputMatterOnly) {
		this.isOutputMatterOnly = outputMatterOnly;
	}

	public OutputMode getOutputMode() {
		return outputMode;
	}

	public void setOutputMode(final OutputMode outputMode) {
		this.outputMode = outputMode;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	public String getOutputPattern() {
		return outputPattern;
	}

	public void setOutputPattern(final String outputPattern) {
		this.outputPattern = outputPattern;
	}

	public boolean isPixelsMode() {
		return pixelsMode;
	}

	public void setPixelsMode(final boolean pixelsMode) {
		this.pixelsMode = pixelsMode;
	}

	public IntPoint[] getPixelsToGetColor() {
		return pixelsToGetColor;
	}

	public void setPixelsToGetColor(final IntPoint[] pixelsToGetColor) {
		this.pixelsToGetColor = pixelsToGetColor;
	}

	public IntPoint[] getPixelsToCompare() {
		return pixelsToCompare;
	}

	public void setPixelsToCompare(final IntPoint[] pixelsToCompare) {
		this.pixelsToCompare = pixelsToCompare;
	}

	public boolean isShowEveryPixelMatch() {
		return showEveryPixelMatch;
	}

	public void setShowEveryPixelMatch(final boolean showEveryPixelMatch) {
		this.showEveryPixelMatch = showEveryPixelMatch;
	}

	public boolean isScreenshotMode() {
		return screenshotMode;
	}

	public void setScreenshotMode(final boolean screenshotMode) {
		this.screenshotMode = screenshotMode;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

	public boolean isInteractiveMode() {
		return interactiveMode;
	}

	public void setInteractiveMode(final boolean interactiveMode) {
		this.interactiveMode = interactiveMode;
	}

	public IntPoint[] getPointsToCalculate() {
		return pointsToCalculate;
	}

	public void setPointsToCalculate(final IntPoint[] pointsToCalculate) {
		this.pointsToCalculate = pointsToCalculate;
	}

	@Override
	public String toString() {
		return "Params{" + "sourceImage=" + sourceImage + ", targetImages=" + Arrays.toString(targetImages)
				+ ", rectangle=" + Arrays.toString(rectangle) + ", accuracy=" + Arrays.toString(accuracy) + ", delay="
				+ delay + ", isOutputMatterOnly=" + isOutputMatterOnly + ", outputMode=" + outputMode + ", delimiter='"
				+ delimiter + '\'' + ", outputPattern='" + outputPattern + '\'' + ", pixelsMode=" + pixelsMode
				+ ", pixelsToGetColor=" + Arrays.toString(pixelsToGetColor) + ", pixelsToCompare=" + Arrays.toString(
				pixelsToCompare) + ", showEveryPixelMatch=" + showEveryPixelMatch + ", screenshotMode=" + screenshotMode
				+ ", outputFile=" + outputFile + ", interactiveMode=" + interactiveMode + ", pointsToCalculate="
				+ Arrays.toString(pointsToCalculate) + '}';
	}
}
