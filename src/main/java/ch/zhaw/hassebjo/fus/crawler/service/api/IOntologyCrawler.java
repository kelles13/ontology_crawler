package ch.zhaw.hassebjo.fus.crawler.service.api;

import java.io.File;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import ch.zhaw.hassebjo.fus.crawler.model.story.BaseUserStory;

public interface IOntologyCrawler {

	List<BaseUserStory> crawl(File ontologyPath) throws OWLOntologyCreationException;
	
}
