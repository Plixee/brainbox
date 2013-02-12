package com.plixee.lab.brainbox.service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.model.User;

@Service
public class PopulateService {
	@Inject
	private IdeaService ideaService;
	@Inject
	private UserService userService;

	@PostConstruct
	public void populate() {
		this.createDefaultCredentials();
		User chuck = this.createChuckNorris();
		this.createPoneyIdea(chuck);
	}

	private void createDefaultCredentials() {
		User user = new User();
		user.setName("test");
		user.setPassword("password");
		this.userService.store(user);
	}

	private User createChuckNorris() {
		User user = new User();
		user.setName("Chuck Norris");
		user.setPassword("returnkick");
		return this.userService.store(user);
	}

	private void createPoneyIdea(User author) {
		Idea idea = new Idea();
		idea.setAuthor(author);
		idea.setTitle("Send a poney to the moon");
		idea.setDescription("Because poneys are great!");
		DateTime date = new DateTime(2013, 2, 12, 17, 49);
		idea.setCreatedDate(date);
		this.ideaService.store(idea);
	}
}
