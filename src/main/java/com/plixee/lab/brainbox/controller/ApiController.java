package com.plixee.lab.brainbox.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.service.IdeaService;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
	@PostConstruct
	public void setUp() {
		// TODO remove this fake populate
		Idea idea1 = new Idea();
		idea1.setAuthor("Chuck Norris");
		idea1.setTitle("Send a poney to the moon");
		idea1.setDescription("Because poneys are great!");
		this.ideaService.store(idea1);

		Idea idea2 = new Idea();
		idea2.setAuthor("Nyan Cat");
		idea2.setTitle("Nyanyanyanyan");
		idea2.setDescription("Nyanyan nyanyan nyanyanyan!");
		this.ideaService.store(idea2);
	}

	@Inject
	private IdeaService ideaService;

	@RequestMapping(value = "/ideas")
	@ResponseBody
	public List<Idea> getIdeas() {
		return this.ideaService.getAll();
	}

	@RequestMapping(value = "/ideas/{id}")
	@ResponseBody
	public Idea getIdea(@PathVariable Long id) {
		return this.ideaService.get(id);
	}

	@RequestMapping(value = "/ideas", method = RequestMethod.POST)
	@ResponseBody
	public Idea postIdea(@RequestBody Idea idea) {
		return this.ideaService.store(idea);
	}
}
