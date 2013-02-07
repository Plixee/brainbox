package com.plixee.lab.brainbox.service;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.repository.IdeaRepository;

@Service
public class IdeaService {
	@Inject
	private IdeaRepository ideaRepository;

	public Idea store(Idea idea) {
		if (idea.getCreatedDate() == null) {
			idea.setCreatedDate(DateTime.now());
		}
		return this.ideaRepository.save(idea);
	}
}
