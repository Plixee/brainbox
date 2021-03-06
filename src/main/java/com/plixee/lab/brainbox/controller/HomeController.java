package com.plixee.lab.brainbox.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.plixee.lab.brainbox.exception.ResourceNotFoundException;
import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.model.User;
import com.plixee.lab.brainbox.service.IdeaService;
import com.plixee.lab.brainbox.service.LoginService;
import com.plixee.lab.brainbox.service.UserService;

@Controller
public class HomeController {
	@Inject
	@Qualifier("org.springframework.security.authenticationManager")
	private AuthenticationManager authenticationManager;
	@Inject
	private IdeaService ideaService;
	@Inject
	private LoginService loginService;
	@Inject
	private UserService userService;

	@RequestMapping(value = "/")
	public String getHome(Model model) {
		this.buildHomeModel(model);
		return "home";
	}

	private void buildHomeModel(Model model) {
		List<Idea> ideas = this.ideaService.getAll();
		model.addAttribute("ideas", ideas);

		User user = this.loginService.getConnectedUser();
		if (user != null) {
			model.addAttribute("user", user);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postIdea(Model model, Idea idea) {
		User user = this.loginService.getConnectedUser();
		if (user == null) {
			model.addAttribute("error",
					"You have to be connected to post an idea.");
		} else {
			idea.setAuthor(user);
			this.ideaService.store(idea);
		}
		return this.getHome(model);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model, User user) {
		String decryptedPassword = user.getPassword();
		this.userService.store(user);
		this.login(request, user.getName(), decryptedPassword);
		return this.getHome(model);
	}

	private void login(HttpServletRequest request, String name, String password) {
		Authentication req = new UsernamePasswordAuthenticationToken(name,
				password);
		Authentication res = this.authenticationManager.authenticate(req);
		SecurityContextHolder.getContext().setAuthentication(res);
		request.getSession()
				.setAttribute(
						HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
						SecurityContextHolder.getContext());
	}

	@RequestMapping(value = "/ideas/{id}/plus")
	public String plus(Model model, @PathVariable Long id) {
		User user = this.loginService.getConnectedUser();
		if (user == null) {
			model.addAttribute("error",
					"You have to be connected to \"plus\" an idea.");
		} else {
			this.ideaService.plus(id, user.getId());
		}
		return this.getHome(model);
	}

	@RequestMapping(value = "/ideas/{id}/minus")
	public String minus(Model model, @PathVariable Long id) {
		User user = this.loginService.getConnectedUser();
		if (user == null) {
			model.addAttribute("error",
					"You have to be connected to \"minus\" an idea.");
		} else {
			this.ideaService.minus(id, user.getId());
		}
		return this.getHome(model);
	}

	@ExceptionHandler
	public ModelAndView handle(HttpServletResponse response, Exception e) {
		// Building a model
		Model model = new ExtendedModelMap();
		model.addAttribute("error",
				"An unexpected error occurred (" + e.getMessage() + ").");
		this.buildHomeModel(model);

		// Defining an HttpStatus
		response.setStatus(this.getStatus(e));

		// Returning a ModelAndView
		ModelAndView mav = new ModelAndView("home");
		mav.addAllObjects(model.asMap());
		return mav;
	}

	private int getStatus(Exception e) {
		HttpStatus status;
		if (e instanceof ConstraintViolationException) {
			status = HttpStatus.BAD_REQUEST;
		} else if (e instanceof ResourceNotFoundException) {
			status = HttpStatus.NOT_FOUND;
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return status.value();
	}
}
