package com.project.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.ecommerce.entities.User;
import com.project.ecommerce.repositories.RoleRepository;
import com.project.ecommerce.repositories.UserRepository;
import com.project.ecommerce.security.JwtHelper;

@SpringBootApplication
@EnableWebMvc
// @EnableSwagger2
public class EcommerceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);

	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository repository;
	@Value("${normal.role.id}")
	private String role_normal_id;
	@Value("${admin.role.id}")
	private String role_admin_id;
	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		System.out.println(passwordEncoder.encode("abcd"));

		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println(encoder.encode("admin123"));
			// Role role_admin =
			// Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			// Role role_normal =
			// Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();

			// User adminUser = User.builder()
			// .name("admin")
			// .email("admin@gmail.com")
			// .password(passwordEncoder.encode("admin123"))
			// .gender("Male")
			// .imageName("default.png")
			// .roles(Set.of(role_admin, role_normal))
			// .userId(UUID.randomUUID().toString())
			// .about("I am admin User")
			// .build();

			// User normalUser = User.builder()
			// .name("normal")
			// .email("normal@gmail.com")
			// .password(passwordEncoder.encode("normal123"))
			// .gender("Male")
			// .imageName("default.png")
			// .roles(Set.of(role_normal))
			// .userId(UUID.randomUUID().toString())
			// .about("I am Normal User")
			// .build();

			// repository.save(role_admin);
			// repository.save(role_normal);

			// userRepository.save(adminUser);
			// userRepository.save(normalUser);

			User user = userRepository.findByEmail("admin@gmail.com").get();

			String token = jwtHelper.generateToken(user);
			System.out.println(token);

			System.out.println("getting username from token Main");

			System.out.println(jwtHelper.getUsernameFromToken(token));

			System.out.println("Token expired: ");
			System.out.println(jwtHelper.isTokenExpired(token));

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Error catch");
			System.out.println(e.getMessage());
		}

	}
}