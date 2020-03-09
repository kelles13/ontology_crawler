package ch.zhaw.hassebjo.fus.crawler.service.impl;

import java.util.List;

import ch.zhaw.hassebjo.fus.crawler.model.story.BaseUserStory;
import ch.zhaw.hassebjo.fus.crawler.service.api.IBaseUserStoryOutputGenerator;

public class BaseUserStoryConsoleOutputGenerator implements IBaseUserStoryOutputGenerator {

	@Override
	public void write(List<BaseUserStory> stories) {
		for (BaseUserStory story : stories) {
			System.out.println(story.toString());
		}
	}

}
