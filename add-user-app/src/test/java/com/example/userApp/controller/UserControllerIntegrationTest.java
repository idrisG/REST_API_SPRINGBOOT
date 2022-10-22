package com.example.userApp.controller;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.userApp.AddUserAppApplication;
import com.example.userApp.model.User;

/**
 * Integration Test Class
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AddUserAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate restTemplate; 
	
	HttpHeaders headers = new HttpHeaders();
	

	/**
	 * Integration test method, create a user in the database using post request to make sure the database has user
	 * Retrieves the same user and assert that the response status is "302 Found"
	 * Tries to retrieve user without username and assert that the responses status is "404 Not Found"
	 */
	@Test
	public void testRetireveUser() {
		HttpEntity<String> entity = new HttpEntity<>(null,headers);
		
		restTemplate.exchange(createUrlWithPort("/createUser"),
				HttpMethod.POST,new HttpEntity<User>(new User("hubert_test","01/01/1996","France","0102030405","male"),
						headers), String.class);
		
		ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/getUser/hubert_test"), 
				HttpMethod.GET, entity, String.class);
		assertEquals(HttpStatus.FOUND,response.getStatusCode());
		
		response = restTemplate.exchange(createUrlWithPort("/getUser/"), 
				HttpMethod.GET, entity, String.class);
		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
	}
	
	/**
	 * Integration test method, creates a user and save it in the database
	 * asserts that the response status is "201 Created".
	 * Tries to save the same user a second time and asserts that the response status is "400 Bad Request"
	 */
	@Test
	public void testCreateUser() {
		User user = new User("hubert_test_post","01/01/1996","France","0102030405","male");
		HttpEntity<User> entity = new HttpEntity<>(user,headers);
		
		ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/createUser"),
				HttpMethod.POST,entity, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		response = restTemplate.exchange(createUrlWithPort("/createUser"),
				HttpMethod.POST,entity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	private String createUrlWithPort(String uri) {
		return "http://localhost:"+port+uri;
	}
	

}
