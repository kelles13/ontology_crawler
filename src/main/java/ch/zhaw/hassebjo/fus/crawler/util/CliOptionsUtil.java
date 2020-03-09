package ch.zhaw.hassebjo.fus.crawler.util;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ch.zhaw.hassebjo.fus.crawler.model.configuration.CliOptions;

/**
 * Command Line Options Util which uses heavily the {@link Options} library.
 * 
 * @author Hasselmann
 */
public class CliOptionsUtil {

	private static final Options OPTIONS = createOptions();
	private static CommandLine CLI;

	public static Set<Option> getAllDefinedOptions() {
		return StaticFieldUtil.read(Option.class, CliOptions.class);
	}

	public static void parseArgs(String[] args) {
		checkHelpOption(args);
		parseOptions(args, createOptions());
	}

	public static boolean hasOption(Option opt) {
		return CLI.hasOption(opt.getOpt());
	}

	public static Optional<String> getOption(Option opt) {
		return Optional.ofNullable(CLI.getOptionValue(opt.getOpt()));
	}

	public static String getOptionRequired(Option opt) {
		return CLI.getOptionValue(opt.getOpt());
	}

	private static void parseOptions(String[] args, Options options) {
		try {
			CLI = new DefaultParser().parse(options, args);
		} catch (ParseException exc) {
			System.err.println(exc.getMessage());
			printHelpText();
			System.exit(-1);
		}
	}

	/**
	 * Args need to be parsed twice, otherwise a {@link MissingOptionException}
	 * contradicts with the help option.
	 */
	private static void checkHelpOption(String[] args) {
		Options options = new Options();
		options.addOption(getHelpOption());
		parseOptions(args, options);
		if (CLI.hasOption(getHelpOption().getOpt())) {
			printHelpText();
			System.exit(0);
		}
	}

	private static Option getHelpOption() {
		return CliOptions.HELP;
	}

	public static void printHelpText() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("FixturePlanner", OPTIONS);
	}

	private static Options createOptions() {
		Options options = new Options();
		for (Option o : getAllDefinedOptions()) {
			options.addOption(o);
		}
		return options;
	}

}
