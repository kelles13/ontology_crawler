package ch.zhaw.hassebjo.fus.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import ch.zhaw.hassebjo.fus.crawler.model.story.BaseUserStory;
import ch.zhaw.hassebjo.fus.crawler.service.impl.Crawler;

@SuppressWarnings("unused")
class CrawlerTest {

	private static final String OWL_PNP = "people+pets.owl.rdfs";
	private static final String OWL_BFO = "bfo.owl";
	private static final String OWL_TEST_REST = "test-restrictions.owl";

	private static final String OWL_PATH = OWL_TEST_REST;

	public static final File getTestOntologyFile() throws FileNotFoundException {
		String testOntPath = CrawlerTest.class.getClassLoader().getResource(OWL_PATH).getFile();
		File file = new File(testOntPath);
		if (!file.exists()) {
			throw new FileNotFoundException(testOntPath);
		}
		return file;
	}

	@Test
	void test() throws OWLOntologyCreationException, FileNotFoundException {
		List<BaseUserStory> stories = new Crawler().crawl(getTestOntologyFile());
		for (BaseUserStory bus : stories) {
			System.out.println(bus.toString());
		}
	}

}
