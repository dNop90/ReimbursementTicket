package com.example.project1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project1.Service.UserService;
import com.example.project1.Entity.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTests {
	@Autowired
	private UserService userService;

	@Test
	@Order(1)
	void registerUser()
	{
		User newuser = userService.registerUser("test", "test");
		Assertions.assertNotEquals(newuser, null);
	}

	@Test
	@Order(2)
	void loginUser()
	{
		User user = userService.getUser("test", "test");
		Assertions.assertNotEquals(user, null);
	}

	@Test
	@Order(3)
	void deleteUser()
	{
		Integer num = userService.deleteUser("test");
		Assertions.assertNotEquals(num, 0);
	}

}
