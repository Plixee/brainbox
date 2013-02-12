package com.plixee.brainbox.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plixee.lab.brainbox.config.AppConfig;
import com.plixee.lab.brainbox.config.SecurityConfig;
import com.plixee.lab.brainbox.model.User;
import com.plixee.lab.brainbox.repository.UserRepository;
import com.plixee.lab.brainbox.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, SecurityConfig.class })
public class UserServiceTest {
	@Inject
	private StandardPasswordEncoder standardPasswordEncoder;
	@Inject
	private UserRepository userRepository;
	@Inject
	private UserService userService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@After
	public void tearDown() {
		this.userRepository.deleteAll();
	}

	@Test
	public void when_a_user_is_stored_an_id_is_defined() {
		// GIVEN
		User user = TestUtils.generateValidUser();

		// WHEN
		User result = this.userService.store(user);

		// THEN
		assertNotNull(result.getId());
	}

	@Test
	public void when_a_user_is_stored_it_returns_the_user_with_an_encrypted_password() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		String initialPassword = user.getPassword();

		// WHEN
		User result = this.userService.store(user);

		// THEN
		assertEquals(user.getName(), result.getName());
		assertTrue(this.standardPasswordEncoder.matches(initialPassword,
				result.getPassword()));
	}

	@Test
	public void when_a_user_is_stored_with_a_null_name_an_exception_is_thrown() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		user.setName(null);

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.userService.store(user);
	}

	@Test
	public void when_a_user_is_stored_with_a_name_with_less_than_2_characters_an_exception_is_thrown() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		user.setName("a");

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.userService.store(user);
	}

	@Test
	public void when_a_user_is_stored_with_a_name_with_more_than_32_characters_an_exception_is_thrown() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		user.setName(TestUtils.generateString(33));

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.userService.store(user);
	}

	@Test
	public void when_a_user_is_stored_with_an_existing_name_an_exception_is_thrown() {
		// GIVEN
		User user1 = TestUtils.generateValidUser();
		user1 = this.userService.store(user1);

		User user2 = TestUtils.generateValidUser();
		user2.setName(user1.getName());

		// THEN
		this.expectedException.expect(PersistenceException.class);

		// WHEN
		this.userService.store(user2);
	}

	@Test
	public void when_a_user_is_stored_with_a_null_password_an_exception_is_thrown() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		user.setPassword(null);

		// THEN
		this.expectedException.expect(IllegalArgumentException.class);

		// WHEN
		this.userService.store(user);
	}

	@Test
	public void when_a_user_is_stored_with_an_password_with_less_than_6_characters_an_exception_is_thrown() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		user.setPassword("a");

		// THEN
		this.expectedException.expect(IllegalArgumentException.class);

		// WHEN
		this.userService.store(user);
	}

	@Test
	public void getByName_can_retrieve_a_stored_user() {
		// GIVEN
		User user = TestUtils.generateValidUser();
		user = this.userService.store(user);

		// WHEN
		User result = this.userService.getByName(user.getName());

		// THEN
		assertEquals(user, result);
	}

	@Test
	public void getByName_an_unexisting_user_returns_null() {
		// GIVEN
		String name = "NyanCat";

		// WHEN
		User result = this.userService.getByName(name);

		// THEN
		assertNull(result);
	}

}
