package com.example.userapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userapp.auth.JwtUtils;
import com.example.userapp.dto.EmployeeDTO;
import com.example.userapp.mapper.EmployeeMapper;
import com.example.userapp.model.Employee;
import com.example.userapp.model.JwtResponse;
import com.example.userapp.model.LoginRequest;
import com.example.userapp.repository.EmployeeRepository;
/**
 * EmployeeService used to take action on employeeRepository.
 * Also used for authentication of API user.
 * @see {@link 
org.springframework.security.core.userdetails.UserDetailsService org.springframework.security.core.userdetails.UserDetailsService}
 * @author idris
 *
 */
@Service("employeeService")
public class EmployeeService implements UserDetailsService {
	private EmployeeRepository employeeRepository;
	private EmployeeMapper employeeMapper;
	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * Constructor, Initialize database with list of employee since database is empty when launching app
	 * @param employeeRepository
	 */
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
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
		list.add(new Employee(0,"idris","password","ADMIN"));
		list.add(new Employee(0,"jean","pass","USER"));
		list.add(new Employee(0,"arnaud","123","USER"));
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
		return employeeMapper.entityToDTO(employeeRepository.save(employeeMapper.dtoToEntity(employeeDTO)));
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
		return employeeMapper.entityToDTO(employeeRepository.findByUsername(username));
	}
	//TODO search a better structure of this part (login has almost nothing to do with employeeService
	/**
	 * 
	 * @param authentication
	 * @return
	 */
	public JwtResponse login(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();		
		return new JwtResponse(jwt, userDetails.getUsername(), 
				userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList());
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeeDTO employeeDTO = findByUsername(username);
		if(employeeDTO == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.withUsername(username).password(employeeDTO.getPassword()).roles(employeeDTO.getRole()).build();
	}
	/**
	 * Password encoder using BCryptPasswordEncoder
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
