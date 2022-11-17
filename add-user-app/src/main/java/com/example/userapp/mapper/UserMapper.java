package com.example.userapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.model.User;

/**
 * MapStruct Mapper for User (entity <-> DTO)
 * @author idris
 *
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	/**
	 * Map User to UserDTO
	 * @param user
	 * @return userDTO type {@link com.example.userapp.dto.UserDTO UserDTO}
	 */
	UserDTO entityToDTO(User user);
	/**
	 * Map userDTO to a User
	 * @param userDTO
	 * @return user type {@link com.example.userapp.model.User User}
	 */
	@Mapping(target = "id", ignore = true)
	User dtoToEntity(UserDTO userDTO);
}
