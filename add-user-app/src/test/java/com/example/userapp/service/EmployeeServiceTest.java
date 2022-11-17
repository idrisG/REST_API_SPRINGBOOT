package com.example.userapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.userapp.dto.EmployeeDTO;
import com.example.userapp.mapper.EmployeeMapper;
import com.example.userapp.model.Employee;
import com.example.userapp.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeService employeeService;
    
    String username = "hubert";
    String password = "password";
    String role = "ADMIN";
    EmployeeDTO employeeDTO = new EmployeeDTO(username,password,role);
    Employee employee = new Employee(0,username,password,role);
    
    /**
     * Unit test find user by username success
     */
    @Test
    void testFindByUsername_success() {
        when(employeeRepository.findByUsername(any(String.class))).thenReturn(employee);
        when(employeeMapper.entityToDTO(employee)).thenReturn(employeeDTO);

        EmployeeDTO foundUser = employeeService.findByUsername(username);
        assertNotNull(foundUser);
        assertThat(foundUser.getUsername()).isSameAs(username);
        assertThat(foundUser.getPassword()).isSameAs(password);
        assertThat(foundUser.getRole()).isSameAs(role);
    }
    /**
     * Unit test create user successfully
     * assert that username is the same, role the same but password changed
     */
    @Test
    void testCreateEmployee_success(){
        when(employeeRepository.save(org.mockito.ArgumentMatchers.any())).thenReturn(employee);
        when(employeeMapper.dtoToEntity(employeeDTO)).thenReturn(employee);
        lenient().when(employeeMapper.entityToDTO(any(Employee.class))).thenReturn(employeeDTO);
        EmployeeDTO savedUser = employeeService.createEmployee(employeeDTO);
        assertNotNull(savedUser);
        assertThat(savedUser.getUsername()).isSameAs(username);
        assertThat(savedUser.getPassword()).isNotEqualTo(password); //Saved employee password encrypted
        assertThat(savedUser.getRole()).isSameAs(role);
    }
}
