package ch.zhaw.hassebjo.fus.crawler.model.configuration;

import org.apache.commons.cli.Option;

public class CliOptions {

	// @formatter:off
	public static final Option HELP = Option.builder("h")
			.longOpt("help")
			.desc("Shows this helper text")
			.build();
	
	public static final Option ONTOLOGY = Option.builder("o")
			.longOpt("owl")
			.hasArg()
			.argName("Ontology Resource")
			.desc("The path or URL to an ontology file (.owl)")
			.required()
			.build();
	// @formatter:on

}
