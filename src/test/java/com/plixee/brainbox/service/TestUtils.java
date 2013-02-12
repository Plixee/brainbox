package com.plixee.brainbox.service;

import com.plixee.lab.brainbox.model.User;

public class TestUtils {
	public static User generateValidUser() {
		User user = new User();
		user.setName("Chuck Norris");
		user.setPassword("returnkick");
		return user;
	}

	public static String generateString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append('a');
		}
		return sb.toString();
	}
}
