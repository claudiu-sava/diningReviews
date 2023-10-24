package com.claudiusava.diningReviews;

import com.claudiusava.diningReviews.model.Role;
import com.claudiusava.diningReviews.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiningReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiningReviewsApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(RoleRepository roleRepo) {
//		return (args) -> {
//			Role adminRole = new Role();
//			adminRole.setRoleName("ROLE_ADMIN");
//			roleRepo.save(adminRole);
//
//			Role userRole = new Role();
//			userRole.setRoleName("ROLE_USER");
//			roleRepo.save(userRole);
//		};
//
//	}

}
