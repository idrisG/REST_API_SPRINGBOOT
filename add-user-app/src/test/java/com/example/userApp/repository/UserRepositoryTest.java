package com.example.userApp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.example.userApp.model.User;
/**
 * Unit test class for repository
 * @author idris
 *
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	/**
	 * Unit test repository, create user success
	 */
	@Test
	@Rollback(false)
	@Order(1)
	void testCreateUser() {
		User user = repository.save(getUser());
		assertThat(user.getId()).isGreaterThan(0);
	}
	/**
	 * Unit test repository, retrieve user success
	 */
	@Test
	@Order(2)
	void testRetireveUser() {
		User user = repository.findByUsername("huber");
		assertThat(user.getUsername()).isEqualTo("huber");
	}
	
	/**
	 * Unit test repository, retrieve user failure
	 */
	@Test
	@Order(3)
	void testRetrieveUserWrongUsername() {
		User user = repository.findByUsername("wrong");
		assertThat(user).isNull();
	}
	
	/**
	 * Create user
	 * @return
	 */
	User getUser() {
		return new User("huber","01/01/2008","Fra","0102030405","male");
	}

}
