package com.plixee.lab.brainbox.controller;

import java.util.List;

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
