package com.plixee.brainbox.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.plixee.lab.brainbox.config.AppConfig;
import com.plixee.lab.brainbox.config.SecurityConfig;
import com.plixee.lab.brainbox.exception.ResourceNotFoundException;
import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.model.User;
import com.plixee.lab.brainbox.repository.IdeaRepository;
import com.plixee.lab.brainbox.repository.UserRepository;
import com.plixee.lab.brainbox.service.IdeaService;
import com.plixee.lab.brainbox.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, SecurityConfig.class })
public class IdeaServiceTest {
	@Inject
	private IdeaRepository ideaRepository;
	@Inject
	private IdeaService ideaService;
	@Inject
	private UserRepository userRepository;
	@Inject
	private UserService userService;

	private User user;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private void emptyDatabase() {
		this.ideaRepository.deleteAll();
		this.userRepository.deleteAll();
	}

	@Before
	public void setUp() {
		this.emptyDatabase();
		this.user = TestUtils.generateValidUser();
		this.user = this.userService.store(user);
	}

	@After
	public void tearDown() {
		DateTimeUtils.setCurrentMillisSystem();
		this.emptyDatabase();
	}

	private Idea generateValidIdea() {
		Idea idea = new Idea();
		idea.setAuthor(this.user);
		idea.setCreatedDate(new DateTime());
		idea.setTitle("Send a poney to the moon");
		idea.setDescription("Because poneys are great!");
		return idea;
	}

	@Test
	public void when_an_idea_is_stored_an_id_is_defined() {
		// GIVEN
		Idea idea = this.generateValidIdea();

		// WHEN
		Idea result = this.ideaService.store(idea);

		// THEN
		assertNotNull(result.getId());
	}

	@Test
	public void when_an_idea_is_stored_without_a_createdDate_the_createdDate_is_defined_to_now() {
		// GIVEN
		DateTime now = new DateTime();
		DateTimeUtils.setCurrentMillisFixed(now.getMillis());

		Idea idea = this.generateValidIdea();

		// WHEN
		Idea result = this.ideaService.store(idea);

		// THEN
		assertEquals(now, result.getCreatedDate());
	}

	@Test
	public void when_an_idea_is_stored_it_returns_the_idea() {
		// GIVEN
		Idea idea = this.generateValidIdea();

		// WHEN
		Idea result = this.ideaService.store(idea);

		// THEN
		assertEquals(idea.getAuthor(), result.getAuthor());
		assertEquals(idea.getCreatedDate(), result.getCreatedDate());
		assertEquals(idea.getTitle(), result.getTitle());
		assertEquals(idea.getDescription(), result.getDescription());
	}

	@Test
	public void when_an_idea_is_stored_with_a_null_author_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setAuthor(null);

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_a_null_title_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setTitle(null);

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_an_empty_title_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setTitle("");

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_a_title_with_more_than_140_characters_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setTitle(TestUtils.generateString(150));

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_a_description_with_1000_characters_it_is_not_truncated() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		String description = TestUtils.generateString(1000);
		idea.setDescription(description);

		// WHEN
		Idea result = this.ideaService.store(idea);

		// THEN
		assertEquals(description, result.getDescription());
	}

	@Test
	public void when_an_idea_is_stored_with_a_description_with_more_than_1000_characters_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setDescription(TestUtils.generateString(1002));

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_an_empty_description_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setDescription("");

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void getAll_returns_all_the_ideas() {
		// GIVEN
		Idea idea1 = this.ideaService.store(this.generateValidIdea());
		Idea idea2 = this.ideaService.store(this.generateValidIdea());

		// WHEN
		List<Idea> ideas = this.ideaService.getAll();

		// THEN
		assertEquals(2, ideas.size());
		assertTrue(ideas.containsAll(Lists.newArrayList(idea1, idea2)));
	}

	@Test
	public void getAll_orders_the_ideas_by_createdDate_DESC() {
		// GIVEN
		DateTime now = new DateTime();

		Idea idea1 = this.generateValidIdea();
		idea1.setCreatedDate(now);
		idea1 = this.ideaService.store(idea1);

		Idea idea2 = this.generateValidIdea();
		idea2.setCreatedDate(now.plusDays(3));
		idea2 = this.ideaService.store(idea2);

		// WHEN
		List<Idea> ideas = this.ideaService.getAll();

		// THEN
		assertEquals(2, ideas.size());
		assertEquals(idea2, ideas.get(0));
		assertEquals(idea1, ideas.get(1));
	}

	@Test
	public void plus_an_idea_adds_the_user_to_the_plus_set() {
		// GIVEN
		Idea idea = this.ideaService.store(generateValidIdea());

		// WHEN
		Idea result = this.ideaService.plus(idea.getId(), user.getId());

		// THEN
		assertTrue(result.getPlus().contains(user));
	}

	@Test
	public void minus_an_idea_adds_the_user_to_the_minus_set() {
		// GIVEN
		Idea idea = this.ideaService.store(generateValidIdea());

		// WHEN
		Idea result = this.ideaService.minus(idea.getId(), user.getId());

		// THEN
		assertTrue(result.getMinus().contains(user));
	}

	@Test
	public void plus_a_minussed_idea_adds_the_user_to_the_plus_set_and_removes_him_from_the_minus_set() {
		// GIVEN
		Idea idea = this.ideaService.store(generateValidIdea());
		idea = this.ideaService.minus(idea.getId(), user.getId());

		// WHEN
		Idea result = this.ideaService.plus(idea.getId(), user.getId());

		// THEN
		assertTrue(result.getPlus().contains(user));
		assertFalse(result.getMinus().contains(user));
	}

	@Test
	public void minus_a_plussed_idea_adds_the_user_to_the_minus_set_and_removes_him_from_the_plus_set() {
		// GIVEN
		Idea idea = this.ideaService.store(generateValidIdea());
		idea = this.ideaService.plus(idea.getId(), user.getId());

		// WHEN
		Idea result = this.ideaService.minus(idea.getId(), user.getId());

		// THEN
		assertTrue(result.getMinus().contains(user));
		assertFalse(result.getPlus().contains(user));
	}

	@Test
	public void plus_an_unexisting_idea_throws_an_exception() {
		// GIVEN
		Long ideaId = 4564556678L;

		// THEN
		this.expectedException.expect(ResourceNotFoundException.class);

		// WHEN
		this.ideaService.plus(ideaId, user.getId());
	}

	@Test
	public void minus_an_unexisting_idea_throws_an_exception() {
		// GIVEN
		Long ideaId = 549876453L;

		// THEN
		this.expectedException.expect(ResourceNotFoundException.class);

		// WHEN
		this.ideaService.minus(ideaId, user.getId());
	}

	@Test
	public void plus_with_an_unexisting_user_throws_an_exception() {
		// GIVEN
		Idea idea = this.ideaService.store(generateValidIdea());
		Long userId = 8754654L;

		// THEN
		this.expectedException.expect(ResourceNotFoundException.class);

		// WHEN
		this.ideaService.plus(idea.getId(), userId);
	}

	@Test
	public void minus_with_an_unexisting_user_throws_an_exception() {
		// GIVEN
		Idea idea = this.ideaService.store(generateValidIdea());
		Long userId = 4566953L;

		// THEN
		this.expectedException.expect(ResourceNotFoundException.class);

		// WHEN
		this.ideaService.minus(idea.getId(), userId);
	}
}
