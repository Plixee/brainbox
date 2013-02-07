package com.plixee.brainbox.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plixee.lab.brainbox.config.AppConfig;
import com.plixee.lab.brainbox.model.Idea;
import com.plixee.lab.brainbox.service.IdeaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class IdeaServiceTest {
	@Inject
	private IdeaService ideaService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@After
	public void tearDown() {
		DateTimeUtils.setCurrentMillisSystem();
	}

	private Idea generateValidIdea() {
		Idea idea = new Idea();
		idea.setAuthor("Chuck Norris");
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
	public void when_an_idea_is_stored_with_an_author_with_less_than_2_characters_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setAuthor("a");

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_an_author_with_more_than_32_characters_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setAuthor(this.generateString(35));

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

	private String generateString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append('a');
		}
		return sb.toString();
	}

	@Test
	public void when_an_idea_is_stored_with_a_title_with_more_than_140_characters_an_exception_is_thrown() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		idea.setTitle(this.generateString(150));

		// THEN
		this.expectedException.expect(ConstraintViolationException.class);

		// WHEN
		this.ideaService.store(idea);
	}

	@Test
	public void when_an_idea_is_stored_with_a_description_with_1000_characters_it_is_not_truncated() {
		// GIVEN
		Idea idea = this.generateValidIdea();
		String description = this.generateString(1000);
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
		idea.setDescription(this.generateString(1002));

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
}
