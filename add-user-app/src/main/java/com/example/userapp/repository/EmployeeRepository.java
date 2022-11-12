package com.example.userapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.userapp.model.Employee;
/**
 * Employee repository extending from CrudRepository
 * @see {@link org.springframework.data.repository.CrudRepository CrudRepository}
 * @author idris
 *
 */
@Repository("employeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	/**
     * Find Employee in database using username
     * 
     * @param username
     * @return Employee if found, null otherwise
     */
    public Employee findByUsername(String username);

}
