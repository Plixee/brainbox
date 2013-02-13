package com.plixee.lab.brainbox.service;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.plixee.lab.brainbox.exception.ResourceNotFoundException;
import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.model.User;
import com.plixee.lab.brainbox.repository.IdeaRepository;
import com.plixee.lab.brainbox.repository.UserRepository;

@Service
public class IdeaService {
	@Inject
	private IdeaRepository ideaRepository;
	@Inject
	private UserRepository userRepository;

	public Idea store(Idea idea) {
		if (idea.getCreatedDate() == null) {
			idea.setCreatedDate(DateTime.now());
		}
		return this.ideaRepository.save(idea);
	}

	public List<Idea> getAll() {
		return this.ideaRepository.findAll(new Sort(Direction.DESC,
				"createdDate"));
	}

	public Idea plus(Long ideaId, Long userId) {
		Idea idea = this.getIdea(ideaId);
		User user = this.getUser(userId);
		idea.getPlus().add(user);
		idea.getMinus().remove(user);
		return this.ideaRepository.save(idea);
	}

	public Idea minus(Long ideaId, Long userId) {
		Idea idea = this.getIdea(ideaId);
		User user = this.getUser(userId);
		idea.getMinus().add(user);
		idea.getPlus().remove(user);
		return this.ideaRepository.save(idea);
	}

	private Idea getIdea(Long ideaId) {
		Idea idea = this.ideaRepository.findOne(ideaId);
		if (idea == null) {
			throw new ResourceNotFoundException();
		}
		return idea;
	}

	private User getUser(Long userId) {
		User user = this.userRepository.findOne(userId);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		return user;
	}
}
