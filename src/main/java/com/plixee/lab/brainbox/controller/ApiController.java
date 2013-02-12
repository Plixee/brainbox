package com.plixee.lab.brainbox.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.model.User;
import com.plixee.lab.brainbox.service.IdeaService;
import com.plixee.lab.brainbox.service.LoginService;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
	@Inject
	private IdeaService ideaService;
	@Inject
	private LoginService loginService;

	@RequestMapping(value = "/ideas")
	@ResponseBody
	public List<Idea> getIdeas() {
		return this.ideaService.getAll();
	}

	@RequestMapping(value = "/ideas", method = RequestMethod.POST)
	@ResponseBody
	public Idea postIdea(@RequestBody Idea idea) {
		User author = this.loginService.getConnectedUser();
		idea.setAuthor(author);
		return this.ideaService.store(idea);
	}
}
