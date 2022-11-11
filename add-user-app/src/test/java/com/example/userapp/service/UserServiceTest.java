package com.example.userapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.Gender;
import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;
import java.util.Optional;

/**
 * Unit test class for UserService
 * 
 * @author idris
 *
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    String username = "hubert";
    LocalDate birthdateDate = LocalDate.of(1996, 01, 01);
    String country = "France";
    String phoneNumber = "0102030405";
    Gender gender = Gender.MALE;

    private UserDTO userDTO = new UserDTO(username, birthdateDate, country, phoneNumber, gender);
    private User user = new User(username, birthdateDate, country, phoneNumber, gender);

    /**
     * Unit test find user by username success
     */
    @Test
    void testFindByUsername_success() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        User foundUser = userService.findByUsername(user.getUsername());
        assertNotNull(foundUser);
        assertThat(foundUser.getUsername()).isSameAs(username);
        assertThat(foundUser.getBirthdate()).isSameAs(birthdateDate);
        assertThat(foundUser.getCountry()).isSameAs(country);
        assertThat(foundUser.getPhoneNumber()).isSameAs(phoneNumber);
        assertThat(foundUser.getGender()).isSameAs(gender);
    }

    /**
     * Unit test save user success
     */
    @Test
    void testCreateUser_success() {
        when(userRepository.save(org.mockito.ArgumentMatchers.any())).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);

        UserDTO savedUser = userService.createUser(userDTO);
        assertNotNull(savedUser);
        assertThat(savedUser.getUsername()).isSameAs(username);
        assertThat(savedUser.getBirthdate()).isSameAs(birthdateDate);
        assertThat(savedUser.getCountry()).isSameAs(country);
        assertThat(savedUser.getPhoneNumber()).isSameAs(phoneNumber);
        assertThat(savedUser.getGender()).isSameAs(gender);
    }

    /**
     * Unit test find by id failure, should return null
     */
    @Test
    void testFindById_failure() {
        lenient().when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThat(userService.findById(0)).isNull();
    }

}
