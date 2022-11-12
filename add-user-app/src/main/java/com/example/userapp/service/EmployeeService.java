package com.example.userapp.service;

import java.util.ArrayList;
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

import com.example.userapp.dto.EmployeeDTO;
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
		list.add(new Employee("idris","password","ADMIN"));
		list.add(new Employee("jean","pass","USER"));
		list.add(new Employee("arnaud","123","USER"));
		return list;
	}
	/**
	 * Save an employee in the database if the username isn't already used
	 * @param employee
	 * @return saved employee if username not used, null otherwise
	 * @see {@link org.springframework.data.repository.CrudRepository#save(Object) CrudRepository.save(Object)}
	 */
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws IllegalArgumentException {
		if(findByUsername(employeeDTO.getUsername())!=null) {
			throw new IllegalArgumentException("Username already used ! ");
		}
		employeeDTO.setPassword(encoder().encode(employeeDTO.getPassword()));
		return entityToDTO(employeeRepository.save(dtoToEntity(employeeDTO)));
	}
	
	/**
	 * Find all instance of Employee in database, and lists their username
	 * @see  {@link org.springframework.data.repository.CrudRepository#findAll() CrudRepository.findAll()}
	 * @return List of the usernames
	 */
	public List<String> findAllUsername(){
		List<String> listEmployeesUsername = new ArrayList<>();
		for(Employee e : employeeRepository.findAll()) {
			listEmployeesUsername.add(e.getUsername());
		}
		return listEmployeesUsername;
	}
	/**
	 * Find Employee by username
	 * @param username
	 * @return Employee if found, null otherwise
	 */
	public EmployeeDTO findByUsername(String username){
		return entityToDTO(employeeRepository.findByUsername(username));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeeDTO employeeDTO = findByUsername(username);
		if(employeeDTO == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.withUsername(username).password(employeeDTO.getPassword()).roles(employeeDTO.getRole()).build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
    /**
     * Method that transform an entity to a dto
     * 
     * @param employee
     * @return
     */
    private EmployeeDTO entityToDTO(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDTO(employee.getUsername(), employee.getPassword(), employee.getRole());
    }

    /**
     * Method that transform a dto to an entity
     * 
     * @param employeeDTO
     * @return
     */
    private Employee dtoToEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return null;
        }
        return new Employee(employeeDTO.getUsername(), employeeDTO.getPassword(), employeeDTO.getRole());
    }
}
