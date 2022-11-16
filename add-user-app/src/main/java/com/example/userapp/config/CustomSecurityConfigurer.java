package com.example.userapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.userapp.auth.MyBasicAuthenticationEntryPoint;
import com.example.userapp.service.EmployeeService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfigurer {

	@Autowired
	private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(employeeService);
	}
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() //Disable necessity of the token, cross domain call possible
			.authorizeRequests()
			.antMatchers("/users/*")
			.permitAll()
			.antMatchers("/employees/login")
			.authenticated()
			.anyRequest() //For all requests
			.authenticated() //need to authenticate
			.and()
			.httpBasic()
			.authenticationEntryPoint(authenticationEntryPoint);
		return http.cors().and().build();
	}
}
