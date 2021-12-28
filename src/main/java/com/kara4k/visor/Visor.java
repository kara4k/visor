package com.kara4k.visor;

import com.kara4k.visor.main.ArgsConverter;
import com.kara4k.visor.main.MainExecutor;
import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.model.OutputMode;
import com.kara4k.visor.model.Params;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "visor", mixinStandardHelpOptions = true, description = "Visor is epic", version = "0.1")
public class Visor implements Callable<Integer> {

	@Parameters(description = "Target image files to search.")
	private File[] targetImages;

	@Option(names = {"-s", "--source"}, description = "Source image file, if not defined take screenshot.")
	private File sourceImage;

	@Option(arity = "0..4", names = {"-r", "--rectangle"}, description = "Define rectangle area [x, y, width, height].")
	private int[] rectangle;

	@Option(arity = "0..3", names = {"-a", "--accuracy"}, description = "RGB accuracy for comparation [R, G, B].")
	private int[] accuracy;

	@Option(names = {"-l", "--delay"}, description = "Delay before capturing screenshot.")
	private long delay;

	@Option(names = {"-m", "--matter-only"},
			description = "Show only center [x,y] coordinates instead [x, y, width, height], or only [R, G, B] in pixel mode.")
	private boolean outputMatterOnly;

	@Option(names = {"-o", "--output-mode"}, defaultValue = "NONE",
			description = "If more than one target files:\n" + "NONE - print without group dividing\n"
					+ "NAME - print target filename before matched coords\n" + "PATH - same as NAME but absolute path\n"
					+ "SPACE - divide groups by space only")
	private OutputMode outputMode;

	@Option(names = {"-d", "--delimiter"}, defaultValue = " ",
			description = "Set delimiter for output coords, space by default.")
	private String delimiter;

	@Option(names = {"-p", "--pattern"},
			description = "Java regexp for output line:\n" + "%%1$d - x\n" + "%%2$d - y\n" + "%%3$d - width\n"
					+ "%%4$d - height\n" + "%%5$d - centerX\n" + "%%6$d - centerY\n" + "%%7$s - target filename\n"
					+ "%%8$s - absolute target file path\n" + "\nPixel mode (-x):\n" + "%%1$d - x\n" + "%%2$d - y\n"
					+ "%%3$d - Red\n" + "%%4$d - Green\n" + "%%5$d - Blue\n" + "%%6$s - source absolute path\n" + "")
	private String outputPattern;

	@Option(names = {"-X", "--pixels-mode"},
			description = "Pixels mode, get pixels colors or compare specified pixels to source, can use -r option.")
	private boolean pixelsMode;

	@Option(arity = "*", names = {"-g", "--get-color"}, description = "Delimiter separated coords [x,y]")
	private String[] pixelsToGetColor;

	@Option(arity = "*", names = {"-c", "--compare-pixels"},
			description = "Delimiter separated coords and expected pixel color [x,y,R,G,B], returns 0 on match, otherwise 1.")
	private String[] pixelsToCompare;

	@Option(names = {"-e", "--every-match"},
			description = "If more than one pixel passed, show if match for every one.")
	private boolean showEveryPixelMatch;

	@Option(names = {"-S", "--screenshot"}, description = "Create screenshot.")
	private boolean screenshotMode;

	@Option(names = {"-f", "--file"}, description = "Target output file.")
	private File outputFile;

	@Option(names = {"-I", "--interactive-mode"},
			description = "Interactive mode. Use 'Alt-S' and 'Alt-E' to save into file coords under mouse pointer")
	private boolean interactiveMode;

	@Option(arity = "*", names = {"-C", "--width-height"},
			description = "Calculate width and height. Delimiter separated coords [x,y]. "
					+ "Output is [width, height]. Can use matter (-m) for center calculation.")
	private String[] pointsToCalculate;

	@Override
	public Integer call() {
		final Params params = createParams();
		MainExecutor.execute(params);
		return 0;
	}

	private Params createParams() {
		final Params params = new Params();
		params.setSourceImage(sourceImage);
		params.setTargetImages(targetImages);
		params.setRectangle(rectangle);
		params.setAccuracy(accuracy);
		params.setDelay(delay);
		params.setOutputMatterOnly(outputMatterOnly);
		params.setOutputMode(outputMode);
		params.setDelimiter(delimiter);
		params.setOutputPattern(outputPattern);
		params.setPixelsMode(pixelsMode);
		if (pixelsMode && pixelsToGetColor != null) {
			final IntPoint[] points = ArgsConverter.convertPixelsToGetColor(delimiter, pixelsToGetColor);
			params.setPixelsToGetColor(points);
		}
		if (pixelsMode && pixelsToCompare != null) {
			final IntPoint[] points = ArgsConverter.convertPixelsToCompare(delimiter, pixelsToCompare);
			params.setPixelsToCompare(points);
		}
		params.setShowEveryPixelMatch(showEveryPixelMatch);
		params.setScreenshotMode(screenshotMode);
		params.setOutputFile(outputFile);
		params.setInteractiveMode(interactiveMode);
		if (pointsToCalculate != null) {
			final IntPoint[] points = ArgsConverter.convertPointsToCalculate(delimiter, pointsToCalculate);
			params.setPointsToCalculate(points);
		}
		return params;
	}

	public static void main(final String[] args) {
		final int exitCode = new CommandLine(new Visor()).execute(args);
		System.exit(exitCode);
	}
}
