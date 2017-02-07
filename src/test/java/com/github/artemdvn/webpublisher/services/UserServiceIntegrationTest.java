package com.github.artemdvn.webpublisher.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.artemdvn.webpublisher.IntegrationTest;
import com.github.artemdvn.webpublisher.model.User;
import com.github.artemdvn.webpublisher.model.UserRole;

@Category(IntegrationTest.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class UserServiceIntegrationTest {

	@Autowired
	UserService userService;

	@Test
	public void userCouldBeGetById() {
		User user = userService.get(1001);
		assertNotNull(user);
	}

	@Test
	public void nullCouldNotBeGet() {
		assertNull(userService.get(null));
	}

	@Test
	public void userCouldNotBeGetIfNotExist() {
		assertNull(userService.get(10001));
	}

	@Test
	public void userCouldBeGetByName() {
		User user = userService.getByName("admin");
		assertEquals("admin", user.getName());
	}

	@Test
	public void userCouldBeGetByRole() {
		List<User> users = userService.getByRole("ADMIN");
		assertNotEquals(0, users.size());
	}

	@Test
	public void allUsersCouldBeGet() {
		assertTrue(userService.getAll().size() > 0);
	}

	@Test
	public void userCouldBeDeletedIfExist() {
		int beforeDel = userService.getAll().size();
		User user = userService.get(1001);
		userService.delete(user);
		int afterDel = userService.getAll().size();
		assertSame(beforeDel - 1, afterDel);
	}

	@Test
	public void userCouldBeCreated() {
		int beforeSave = userService.getAll().size();
		User user = new User(555, "test", "test@webpublisher.com", UserRole.PUBLISHER);
		userService.create(user);
		int afterSave = userService.getAll().size();
		assertSame(beforeSave + 1, afterSave);
	}

	@Test
	public void userCouldBeUpdateIfExist() {
		User user = new User(555, "test123", "test123@webpublisher.com", UserRole.PUBLISHER);
		userService.create(user);

		user.setName("new name");
		userService.update(user);
		
		assertEquals("new name", user.getName());
	}

}
