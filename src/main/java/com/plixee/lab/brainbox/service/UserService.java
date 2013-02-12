package com.plixee.lab.brainbox.service;

import javax.inject.Inject;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.plixee.lab.brainbox.model.User;
import com.plixee.lab.brainbox.repository.UserRepository;

@Service
public class UserService {
	@Inject
	private StandardPasswordEncoder standardPasswordEncoder;
	@Inject
	private UserRepository userRepository;

	public User store(User user) {
		if (user.getPassword() == null || user.getPassword().length() < 6) {
			throw new IllegalArgumentException();
		}
		String encodedPassword = this.standardPasswordEncoder.encode(user
				.getPassword());
		user.setPassword(encodedPassword);
		return this.userRepository.save(user);
	}

	public User getByName(String name) {
		return this.userRepository.findByName(name);
	}
}
