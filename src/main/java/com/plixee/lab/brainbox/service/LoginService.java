package com.plixee.lab.brainbox.service;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plixee.lab.brainbox.model.User;

@Service
public class LoginService implements UserDetailsService {
	@Inject
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String name)
			throws UsernameNotFoundException {
		return this.userService.getByName(name);
	}

	public User getConnectedUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String) {
			return null;
		} else {
			// Fetch the database to have a persistent User
			User user = this.userService.getByName(((UserDetails) principal)
					.getUsername());
			return user;
		}
	}
}
