package ch.zhaw.hassebjo.fus.crawler.service.api;

import java.io.IOException;
import java.util.List;

import ch.zhaw.hassebjo.fus.crawler.model.story.BaseUserStory;

public interface IBaseUserStoryOutputGenerator {

	void write(List<BaseUserStory> stories) throws IOException;
	
}
