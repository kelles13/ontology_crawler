package ch.zhaw.hassebjo.fus.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import ch.zhaw.hassebjo.fus.crawler.model.configuration.CliOptions;
import ch.zhaw.hassebjo.fus.crawler.model.story.BaseUserStory;
import ch.zhaw.hassebjo.fus.crawler.service.api.IBaseUserStoryOutputGenerator;
import ch.zhaw.hassebjo.fus.crawler.service.api.IOntologyCrawler;
import ch.zhaw.hassebjo.fus.crawler.service.impl.BaseUserStoryConsoleOutputGenerator;
import ch.zhaw.hassebjo.fus.crawler.service.impl.Crawler;
import ch.zhaw.hassebjo.fus.crawler.util.CliOptionsUtil;

public class Runner {

	/**
	 * @throws OWLOntologyCreationException
	 * @throws IOException
	 */
	public static void main(String[] args) throws OWLOntologyCreationException, IOException {
		CliOptionsUtil.parseArgs(args);

		List<BaseUserStory> stories = createCrawler()
				.crawl(getFileFromPath(CliOptionsUtil.getOptionRequired(CliOptions.ONTOLOGY)));
		createOutputGenerator().write(stories);

	}

	private static File getFileFromPath(String path) throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException(path);
		}
		return file;
	}

	public static final IOntologyCrawler createCrawler() {
		return new Crawler();
	}

	public static final IBaseUserStoryOutputGenerator createOutputGenerator() {
		return new BaseUserStoryConsoleOutputGenerator();
	}

}
