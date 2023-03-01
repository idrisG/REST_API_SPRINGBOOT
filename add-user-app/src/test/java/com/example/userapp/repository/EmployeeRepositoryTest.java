package com.example.userapp.repository;
import static org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.userapp.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/**
 * Unit test class for repository (entity included)
 * 
 * @author idris
 *
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository repository;
    
    /**
     * Unit test repository, create employee success
     */
    @Test
    @Rollback(false)
    @Order(1)
    void testCreateUser_success() {
        Employee employee = repository.save(new Employee(0,"idris","password","USER"));
        assertThat(employee.getUsername()).isEqualTo("idris");
    }

    /**
     * Unit test repository, retrieve employee success
     */
    @Test
    @Order(2)
    void testRetireveUser_success() {
        Employee employee = repository.findByUsername("idris");
        assertThat(employee.getUsername()).isEqualTo("idris");
    }

    /**
     * Unit test repository, retrieve employee failure
     */
    @Test
    @Order(3)
    void testRetrieveUser_failureWrongUsername() {
        Employee employee = repository.findByUsername("wrong username");
        assertThat(employee).isNull();
    }

    /**
     * Test to create employee with null username Asserts that it throws
     * ConstraintViolationException
     */
    @Test
    @Order(5)
    void testCreateUser_failureInvalidNullUsername() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> repository.save(new Employee(null,null,"","")));
    }

}
