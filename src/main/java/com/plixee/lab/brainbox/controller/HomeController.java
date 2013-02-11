package com.plixee.lab.brainbox.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.service.IdeaService;

@Controller
public class HomeController {
	@Inject
	private IdeaService ideaService;

	@RequestMapping(value = "/")
	public String getHome(Model model) {
		List<Idea> ideas = this.ideaService.getAll();
		model.addAttribute("ideas", ideas);
		return "home";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postIdea(Model model, Idea idea) {
		this.ideaService.store(idea);
		return this.getHome(model);
	}
}
