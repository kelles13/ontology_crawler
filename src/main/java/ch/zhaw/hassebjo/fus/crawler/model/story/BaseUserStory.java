package ch.zhaw.hassebjo.fus.crawler.model.story;

public class BaseUserStory {

	private String subject, verb, object;

	public BaseUserStory(String subject, String verb, String object) {
		super();
		this.subject = subject;
		this.verb = verb;
		this.object = object;
	}

	public String getSubject() {
		return subject;
	}

	public String getVerb() {
		return verb;
	}

	public String getObject() {
		return object;
	}
	
	@Override
	public String toString() {
		return "As a " + getSubject() + " I want to " + getVerb() + " " + getObject();
	}

}
