package com.plixee.lab.brainbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plixee.lab.brainbox.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByName(String name);
}
