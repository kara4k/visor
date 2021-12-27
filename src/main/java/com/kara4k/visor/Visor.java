package com.kara4k.visor;

import com.kara4k.visor.model.IntPoint;
import com.kara4k.visor.model.OutputMode;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.processor.MainExecutor;
import com.kara4k.visor.util.ArgsConverter;
import com.kara4k.visor.util.PixelComporator;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "visor", mixinStandardHelpOptions = true, description = "Visor is epic", version = "0.1")
// TODO: 12/26/21 help text,
public class Visor implements Callable<Integer> {

	private final static Logger logger = Logger.getLogger(PixelComporator.class.getName());

	static {
		final Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.INFO);
	}

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
	private boolean outputCenterOnly;

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
					+ "%%4$d - height\n" + "%%5$d - centerX\n" + "%%6$d - centerY\n" + "%%7$s - filename\n"
					+ "%%8$s - absolute file path\n" + "\nPixel mode (-x):\n" + "%%1$d - x\n" + "%%2$d - y\n"
					+ "%%3$d - Red\n" + "%%4$d - Green\n" + "%%5$d - Blue\n" + "%%6$s - source absolute path\n" + "")
	private String outputPattern;

	@Option(names = {"-x", " --pixels-mode"},
			description = "Pixels mode, get pixels colors or compare specified pixels to source, can use -r option.")
	private boolean pixelsMode;

	@Option(arity = "*", names = {"-g", "--get-color"}, description = "Comma separated coords [x,y]")
	private String[] pixelsToGetColor;

	@Option(arity = "*", names = {"-C", "--compare-pixels"},
			description = "Comma separated coords and expected pixel color [x,y,R,G,B], returns 0 on match, otherwise 1.")
	private String[] pixelsToCompare;

	@Option(names = {"-e", "--every-match"},
			description = "If more than one pixel passed, show if match for every one.")
	private boolean showEveryPixelMatch;

	private final MainExecutor mainExecutor = new MainExecutor();

	@Override
	public Integer call() {
		final Params params = createParams();
		logger.info(params.toString());
		mainExecutor.execute(params);
		return 0;
	}

	private Params createParams() {
		final Params params = new Params();
		params.setSourceImage(sourceImage);
		params.setTargetImages(targetImages);
		params.setRectangle(rectangle);
		params.setAccuracy(accuracy);
		params.setDelay(delay);
		params.setOutputCenterOnly(outputCenterOnly);
		params.setOutputMode(outputMode);
		params.setDelimiter(delimiter);
		params.setOutputPattern(outputPattern);
		params.setPixelsMode(pixelsMode);
		if (pixelsMode && pixelsToGetColor != null) {
			final IntPoint[] points = ArgsConverter.convertPixelsToGetColor(pixelsToGetColor);
			params.setPixelsToGetColor(points);
		}
		if (pixelsMode && pixelsToCompare != null) {
			final IntPoint[] points = ArgsConverter.convertPixelsToCompare(pixelsToCompare);
			params.setPixelsToCompare(points);
		}
		params.setShowEveryPixelMatch(showEveryPixelMatch);
		return params;
	}

	public static void main(final String[] args) {
		final int exitCode = new CommandLine(new Visor()).execute(args);
		System.exit(exitCode);
	}
}
