package com.example.userapp.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import com.example.userapp.dto.EmployeeDTO;
import com.example.userapp.service.EmployeeService;

/**
 * Unit test class for controller with JSON form request and response
 * 
 * @author idris
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@WithMockUser
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeControllerTest {
	
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService employeeService;

    EmployeeDTO employeeDTO = new EmployeeDTO("Idriss","pass", "ADMIN");

    String exampleEmployeeJson = "{\r\n"
    		+ "    \"username\": \"Idriss\",\r\n"
    		+ "    \"password\": \"pass\"\r\n"
    		+ "}";
    
    String exampleVoidEmployeeJson = "{}";

    /**
     * JUnit test for creating User, POST request.
     * 
     * @throws Exception
     */
    @Test
    @Rollback(false)
    @Order(1)
    @WithMockUser(roles="ADMIN")
    void testCreateEmployee_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employees")
        		.with(csrf())
        		.accept(MediaType.APPLICATION_JSON)
                .content(exampleEmployeeJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    /**
     * JUnit test for creating Employee with already used username , POST request
     * 
     * @throws Exception
     */
    @Test
    @Order(2)
    void testCreateEmployee_failureInvalidEmployeeUsernameAlreadyUsed() throws Exception {
        Mockito.when(employeeService.createEmployee(any())).thenThrow(new IllegalArgumentException("username already used ! "));
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employees")
        		.with(csrf())
        		.accept(MediaType.APPLICATION_JSON)
                .content(exampleEmployeeJson)
                .contentType(MediaType.APPLICATION_JSON);
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
    @WithMockUser(roles="ADMIN")
    void testCreateUser_failureVoidUser() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employees")
        		.with(csrf())
        		.accept(MediaType.APPLICATION_JSON)
                .content(exampleVoidEmployeeJson)
                .contentType(MediaType.APPLICATION_JSON);
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
    @WithMockUser(roles="ADMIN")
    void testRetireveEmployees_success() throws Exception {
    	List<String> usernames = new ArrayList<>();
    	usernames.add("idris");
        Mockito.when(employeeService.findAllUsername()).thenReturn(usernames);
        mvc.perform(MockMvcRequestBuilders.get("/employees")
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

}
