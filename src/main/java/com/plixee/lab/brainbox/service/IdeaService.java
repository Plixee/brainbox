package com.plixee.lab.brainbox.service;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

	public Idea get(Long id) {
		return this.ideaRepository.findOne(id);
	}

	public List<Idea> getAll() {
		return this.ideaRepository.findAll(new Sort(Direction.DESC,
				"createdDate"));
	}
}
