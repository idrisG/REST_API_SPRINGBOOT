package com.example.userapp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.Gender;
import com.example.userapp.service.UserService;
import com.example.userapp.validator.UserValidator;

/**
 * Unit test class for controller with JSON form request and response
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(UserValidator.class)
@WithMockUser
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	UserDTO userDTO = new UserDTO("hubert",LocalDate.of(1996, 01, 01),"France","0102030405",Gender.MALE);

	String exampleUserJson = "{\r\n"
			+ "    \"username\": \"hubert\",\r\n"
			+ "    \"birthdate\": \"1996-01-01\",\r\n"
			+ "    \"country\":\"France\",\r\n"
			+ "    \"phoneNumber\":\"0102030405\",\r\n"
			+ "    \"gender\":\"MALE\"\r\n"
			+ "}";	
	
	String exampleInvalidUserJson = "{\r\n"
			+ "    \"username\": \"\",\r\n"
			+ "    \"birthdate\": \"1996-01-01\",\r\n"
			+ "    \"country\":\"France\",\r\n"
			+ "    \"phoneNumber\":\"0102030405\",\r\n"
			+ "    \"gender\":\"male\"\r\n"
			+ "}";	
	
	String exampleVoidUserJson = "{}";	
	
	/**
	 * JUnit test for creating User, POST request.
	 * @throws Exception
	 */
	@Test
	@Rollback(false)
	@Order(1)
	void testCreateUser_success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(),response.getStatus());		
	}
	
	/**
	 * JUnit test for creating Invalid User, POST request
	 * @throws Exception
	 */
	@Test
	void testCreateUser_failureInvalidUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users")
				.accept(MediaType.APPLICATION_JSON).content(exampleInvalidUserJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());		
	}
	
	/**
	 * JUnit test for creating void User, POST request.
	 * @throws Exception
	 */
	@Test
	void testCreateUser_failureVoidUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users")
				.accept(MediaType.APPLICATION_JSON).content(exampleVoidUserJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());		
	}
	
	/**
	 * JUnit test for retrieving User, GET request
	 * @throws Exception
	 */
	@Test
	@Order(2)
	void testRetireveUser_success() throws Exception {
		Mockito.when(userService.findById(1)).thenReturn(userDTO);
	    mvc.perform(MockMvcRequestBuilders
	            .get("/users/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isFound())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.username", is(userDTO.getUsername())));
	}
	
}
