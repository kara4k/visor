package com.kara4k.visor.model;

import java.io.File;
import java.util.Arrays;

public class Params {

	private File sourceImage;
	private File[] targetImages; // TODO: 12/26/21 uri?
	private int[] rectangle;
	private int[] accuracy;
	private long delay;
	private boolean outputCenterOnly;
	private OutputMode outputMode;
	private String delimiter;
	private String outputPattern;

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

	public boolean isOutputCenterOnly() {
		return outputCenterOnly;
	}

	public void setOutputCenterOnly(final boolean outputCenterOnly) {
		this.outputCenterOnly = outputCenterOnly;
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

	@Override
	public String toString() {
		return "Params{" + "sourceImage=" + sourceImage + ", targetImages=" + Arrays.toString(targetImages)
				+ ", rectangle=" + Arrays.toString(rectangle) + ", accuracy=" + Arrays.toString(accuracy) + ", delay="
				+ delay + ", outputCenterOnly=" + outputCenterOnly + ", outputMode=" + outputMode + ", delimiter='"
				+ delimiter + '\'' + ", outputPattern='" + outputPattern + '\'' + '}';
	}
}
