package ch.zhaw.hassebjo.fus.crawler.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ch.zhaw.hassebjo.fus.crawler.model.story.BaseUserStory;
import ch.zhaw.hassebjo.fus.crawler.service.api.IOntologyCrawler;

public class Crawler implements IOntologyCrawler {

	private final OWLOntologyManager manager;

	public Crawler() {
		this.manager = OWLManager.createOWLOntologyManager();
	}

	@Override
	public List<BaseUserStory> crawl(File ontologyPath) throws OWLOntologyCreationException {
		return this.crawl(manager.loadOntologyFromOntologyDocument(new FileDocumentSource(ontologyPath)));
	}

	private List<BaseUserStory> crawl(OWLOntology ontology) {

		// We want to examine the restrictions on all classes.
		Set<OWLClass> classes = ontology.classesInSignature().collect(Collectors.toSet());
		List<BaseUserStory> stories = new ArrayList<>(classes.size() * 2); // random guess, better than '10'
		for (OWLClass c : classes) {
			assert c != null;
			// collect the properties which are used in existential restrictions
			RestrictionVisitor visitor = new RestrictionVisitor(ontology);

			/* Ask our superclass to accept a visit from the RestrictionVisitor */
			ontology.subClassAxiomsForSubClass(c).forEach(ax -> ax.getSuperClass().accept(visitor));

			// Our RestrictionVisitor has now collected all of the properties
			// that have been restricted in existential restrictions
			Set<OWLObjectSomeValuesFrom> restrictions = visitor.getRestrictions();
			for (OWLObjectSomeValuesFrom rst : restrictions) {
				BaseUserStory bus = new BaseUserStory(c.getIRI().getShortForm(),
						rst.getProperty().asOWLObjectProperty().getIRI().getShortForm(),
						rst.getFiller().asOWLClass().getIRI().getShortForm());
				stories.add(bus);
			}

		}

		return stories;
	}

	/**
	 * Visits existential restrictions and collects the properties which are
	 * restricted
	 */
	private static class RestrictionVisitor implements OWLObjectVisitor {

		private final Set<OWLClass> processedClasses;
		private final Set<OWLObjectSomeValuesFrom> restrictions;
		private final Set<OWLOntology> onts;

		RestrictionVisitor(OWLOntology... onts) {
			restrictions = new HashSet<>();
			processedClasses = new HashSet<>();
			this.onts = Set.of(onts);
		}

		@Nonnull
		public Set<OWLObjectSomeValuesFrom> getRestrictions() {
			return restrictions;
		}

		@Override
		public void visit(OWLClass ce) {
			// avoid cycles
			if (!processedClasses.contains(ce)) {
				// If we are processing inherited restrictions then we recursively visit named
				// supers.
				processedClasses.add(ce);
				for (OWLOntology ont : onts) {
					ont.subClassAxiomsForSubClass(ce).forEach(ax -> ax.getSuperClass().accept(this));
				}
			}
		}

		@Override
		public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
			/*
			 * This method gets called when a class expression is an existential
			 * (someValuesFrom) restriction and it asks us to visit it
			 */
			// ce.getFiller() // the accusative object
			restrictions.add(ce); // the verb
		}
	}

}
