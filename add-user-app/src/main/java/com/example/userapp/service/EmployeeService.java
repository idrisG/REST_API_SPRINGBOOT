package com.example.userapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userapp.model.Employee;
import com.example.userapp.repository.EmployeeRepository;

@Service("employeeService")
public class EmployeeService implements UserDetailsService {
	private EmployeeRepository employeeRepository;

	/**
	 * Constructor, Initialize database with list of employee since database is empty when launching app
	 * @param employeeRepository
	 */
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
		this.employeeRepository.saveAll(loadEmployee()
				.stream()
				.map(e->{
					e.setPassword(encoder().encode(e.getPassword())); 
					return e;
					})
				.toList());
	}
	/**
	 * Create list of employee to initialize in our employee database
	 * @return
	 */
	private List<Employee> loadEmployee(){
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("Idris","password","USER"));
		list.add(new Employee("Jean","pass","USER"));
		list.add(new Employee("Arnaud","123","USER"));
		return list;
	}
	/**
	 * Save an employee in the database if the username isn't already used
	 * @param employee
	 * @return
	 * @throws Exception
	 * @see {@link org.springframework.data.repository.CrudRepository#save(Object) CrudRepository.save(Object)}
	 */
	//TODO create exception specifique to username already used or deal with this in a validator
	public Employee createEmployee(Employee employee) throws Exception {
		if(findByUsername(employee.getUsername())!=null) {
			throw new Exception();
		}
		employee.setPassword(encoder().encode(employee.getPassword()));
		return employeeRepository.save(employee);
	}
	
	/**
	 * find all instance of Employee in database,
	 * @see  {@link org.springframework.data.repository.CrudRepository#findAll() CrudRepository.findAll()}
	 * @return
	 */
	public Iterable<Employee> findAll(){
		return employeeRepository.findAll();
	}
	/**
	 * Find Employee by username
	 * @param username
	 * @return Employee if found, null otherwise
	 */
	public Employee findByUsername(String username){
		return employeeRepository.findByUsername(username);
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username);
		if(employee == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.withUsername(username).password(employee.getPassword()).roles(employee.getPosition()).build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
