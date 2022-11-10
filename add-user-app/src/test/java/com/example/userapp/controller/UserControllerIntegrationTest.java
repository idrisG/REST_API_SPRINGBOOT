package com.example.userapp.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

import com.example.userapp.AddUserAppApplication;
import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.Gender;

/**
 * Integration Test Class
 * 
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AddUserAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    UserDTO user = new UserDTO("hubert_test", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE);
    UserDTO user_wrong = new UserDTO("hubert_test!!!", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE);

    /**
     * Integration test method, creates a user and save it in the database asserts
     * that the response status is "201 Created". Tries to a user with wrong parameters
     * asserts that the response status is "400 Bad Request"
     */
    @Test
    @Order(1)
    public void testCreateUser() {
        HttpEntity<UserDTO> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/users"), HttpMethod.POST, entity,
                String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        entity = new HttpEntity<>(user_wrong, headers);
        response = restTemplate.exchange(createUrlWithPort("/users"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Integration test method retrieves the user created while testing the create
     * user method and assert that the response status is "302 Found".
     * 
     * Then tries to retrieve user using wrong id and assert that the
     * responses status is "404 Not Found"
     */
    @Test
    @Order(2)
    public void testRetireveUser() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/users/1"), HttpMethod.GET, entity,
                String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());

        response = restTemplate.exchange(createUrlWithPort("/users/10"), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Return full url with port and uri
     * 
     * @param uri
     * @return
     */
    private String createUrlWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
