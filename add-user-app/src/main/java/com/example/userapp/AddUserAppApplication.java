package com.example.userapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main App
 * 
 * @author idris
 *
 */
@SpringBootApplication
public class AddUserAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddUserAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}