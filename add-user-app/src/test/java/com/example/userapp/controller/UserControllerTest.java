package com.example.userapp.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.userapp.security.JwtTokenFilter;
import com.example.userapp.security.JwtUtils;
import com.example.userapp.security.CustomSecurityConfigurer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
import com.example.userapp.service.EmployeeService;
import com.example.userapp.service.UserService;
import com.example.userapp.validator.UserValidator;

/**
 * Unit test class for controller with JSON form request and response
 * 
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JwtTokenFilter.class, JwtUtils.class, CustomSecurityConfigurer.class}))
@Import(UserValidator.class)
@WithMockUser
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EmployeeService employeeService;

    UserDTO userDTO = new UserDTO(0,"hubert", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE);

    String exampleUserJson = "{\r\n" + "    \"username\": \"hubert\",\r\n" + "    \"birthdate\": \"1996-01-01\",\r\n"
            + "    \"country\":\"France\",\r\n" + "    \"phoneNumber\":\"0102030405\",\r\n"
            + "    \"gender\":\"MALE\"\r\n" + "}";

    String exampleInvalidUserJson = "{\r\n" + "    \"username\": \"\",\r\n" + "    \"birthdate\": \"1996-01-01\",\r\n"
            + "    \"country\":\"France\",\r\n" + "    \"phoneNumber\":\"0102030405\",\r\n"
            + "    \"gender\":\"male\"\r\n" + "}";

    String exampleVoidUserJson = "{}";

    /**
     * JUnit test for creating User, POST request.
     * 
     * @throws Exception
     */
    @Test
    @Rollback(false)
    @Order(1)
    void testCreateUser_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
        		.with(csrf())
        		.accept(MediaType.APPLICATION_JSON)
                .content(exampleUserJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    /**
     * JUnit test for creating Invalid User, POST request
     * 
     * @throws Exception
     */
    @Test
    void testCreateUser_failureInvalidUser() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
        		.with(csrf())
        		.accept(MediaType.APPLICATION_JSON)
                .content(exampleInvalidUserJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /**
     * JUnit test for creating void User, POST request.
     * 
     * @throws Exception
     */
    @Test
    void testCreateUser_failureVoidUser() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
        		.with(csrf())
        		.accept(MediaType.APPLICATION_JSON)
                .content(exampleVoidUserJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /**
     * JUnit test for retrieving User, GET request
     * 
     * @throws Exception
     */
    @Test
    @Order(2)
    void testRetireveUser_success() throws Exception {
        Mockito.when(userService.findById(1)).thenReturn(userDTO);
        mvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is(userDTO.getUsername())));
    }
    
    /**
     * JUnit test for retrieving User, GET request
     * 
     * @throws Exception
     */
    @Test
    void testRetireveAllUser_success() throws Exception {
    	List<UserDTO> listUserDTO = Arrays.asList(new UserDTO(1,"hubert", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE), new UserDTO(2,"hubert", LocalDate.of(1996, 01, 01), "France", "0102030405", Gender.MALE));
    	Mockito.when(userService.findAll()).thenReturn(listUserDTO);
        mvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.[0].id",is(1)))
                .andExpect(jsonPath("$.[1].id",is(2)))
                .andDo(print());
    }
    /**
     * JUnit test for retrieving all users, GET request with no user previously saved
     * Expect status to be OK 200 and body content to be an empty array []
     * @throws Exception
     */
    @Test
    void testRetireveAllUser_void() throws Exception {
    	Mockito.when(userService.findAll()).thenReturn(Collections.emptyList());
        mvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"))
                .andDo(print());
    }

}
