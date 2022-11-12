package com.example.userapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.userapp.model.Employee;

@Repository("employeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    public Employee findByUsername(String username);

}
