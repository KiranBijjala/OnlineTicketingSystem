package com.capstone.ticket.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.capstone.ticket.model.User;

class UserTest {

	private User user = new User(1, "Alex", "Password", "2091234567");

	@Test
	void testUserId() {
		assertEquals(user.getId(), 1);
	}

	@Test
	void testUserName() {
		assertEquals(user.getName(), "Alex");
	}

	@Test
	void testUserPassword() {
		assertEquals(user.getPassword(), "Password");
	}

	@Test
	void testUserContact() {
		assertEquals(user.getContactNumber(), "2091234567");
	}

	@Test
	void testSetUserId() {
		user.setId(2);
		assertEquals(user.getId(), 2);
	}

	@Test
	void testSetUserName() {
		user.setName("Timothy");
		assertEquals(user.getName(), "Timothy");
	}

	@Test
	void testSetUserPassword() {
		user.setPassword("newPassword");
		assertEquals(user.getPassword(), "newPassword");
	}

	@Test
	void testSetUserContact() {
		user.setContactNumber("2093234312");
		assertEquals(user.getContactNumber(), "2093234312");
	}

}
