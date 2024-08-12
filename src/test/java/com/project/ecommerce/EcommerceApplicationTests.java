package com.project.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.ecommerce.entities.User;
import com.project.ecommerce.repositories.UserRepository;
import com.project.ecommerce.security.JwtHelper;

@SpringBootTest
class EcommerceApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtHelper jwtHelper;

	@Test
	void contextLoads() {
	}

	@Test
	void testToken() {

		User user = userRepository.findByEmail("admin@gmail.com").get();

		String token = jwtHelper.generateToken(user);
		System.out.println(token);

		System.out.println("getting username from token");

		System.out.println(jwtHelper.getUsernameFromToken(token));

		System.out.println("Token expired: ");
		System.out.println(jwtHelper.isTokenExpired(token));

	}
}
