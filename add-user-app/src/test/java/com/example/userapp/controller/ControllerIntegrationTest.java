package com.example.userapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.example.userapp.dto.EmployeeDTO;
import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.Gender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration Test Class
 * 
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AddUserAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class ControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    UserDTO user = new UserDTO("hubert_test", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE);
    UserDTO user_wrong = new UserDTO("hubert_test!!!", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE);
    EmployeeDTO employee = new EmployeeDTO("hubert","pass","USER");
    /**
     * Integration test method, creates a user and save it in the database asserts
     * that the response status is "201 Created". Tries to save a user with wrong parameters
     * asserts that the response status is "400 Bad Request"
     */
    @Test
    @Order(1)
    public void testCreateUser() {
        HttpEntity<UserDTO> entity = new HttpEntity<>(user, headers);
        restTemplate = new TestRestTemplate("idris","password");
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
        restTemplate = new TestRestTemplate("idris","password");
        ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/users/1"), HttpMethod.GET, entity,
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        response = restTemplate.exchange(createUrlWithPort("/users/10"), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    /**
     * Integration test method, creates a employee and save it in the database asserts
     * that the response status is "201 Created". Tries to save the same employee
     * asserts that the response status is "400 Bad Request"
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    @Test
    @Order(3)
    public void testCreateEmployee() throws JsonMappingException, JsonProcessingException {
        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(employee, headers);
        restTemplate = new TestRestTemplate("idris","password");
        ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/employees"), HttpMethod.POST, entity,
                String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    	ObjectMapper mapper = new ObjectMapper();
    	EmployeeDTO employee = mapper.readValue(response.getBody(), EmployeeDTO.class);
        assertThat(employee.getPassword()).isNotEqualTo("password");

        response = restTemplate.exchange(createUrlWithPort("/employees"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    /**
     * Integration test method, retrieve all usernames of stored employees 
     * and assert that the response status is "302 Found".
     */
    @Test
    @Order(4)
    public void testRetrieveEmployee() {
        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(employee, headers);
        restTemplate = new TestRestTemplate("idris","password");
        ResponseEntity<String> response = restTemplate.exchange(createUrlWithPort("/employees"), HttpMethod.GET, entity,
                String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
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
