package com.example.userApp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.example.userApp.model.User;
import com.example.userApp.service.UserService;
import com.example.userApp.validator.UserValidator;

/**
 * Unit test class for controller with JSON form request and response
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(UserValidator.class)
@WithMockUser
class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	User user = new User("hubert","01/01/1996","France","0102030405","male");
	
	String exampleUserJson = "{\r\n"
			+ "    \"username\": \"hubert\",\r\n"
			+ "    \"birthdate\": \"01/01/1996\",\r\n"
			+ "    \"country\":\"France\",\r\n"
			+ "    \"phoneNumber\":\"0102030405\",\r\n"
			+ "    \"gender\":\"male\"\r\n"
			+ "}";	
	
	String exampleInvalidUserJson = "{\r\n"
			+ "    \"username\": \"\",\r\n"
			+ "    \"birthdate\": \"01/01/1996\",\r\n"
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
	void testCreateUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createUser")
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
	void testCreateUser_invalidUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createUser")
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
	void testCreateUser_voidUser() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createUser")
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
	void testRetireveUser() throws Exception {
		Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(user);
	    mvc.perform(MockMvcRequestBuilders
	            .get("/getUser/"+user.getUsername())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isFound())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.username", is(user.getUsername())));
	}
	
	/**
	 * JUnit test for retrieving User wrong username, GET request
	 * @throws Exception
	 */
	@Test
	void testRetireveUser_invalidUser() throws Exception {
		Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(user);
	    mvc.perform(MockMvcRequestBuilders
	            .get("/getUser/wrongUser")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}
}
