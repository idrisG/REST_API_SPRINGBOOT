package com.example.userapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.example.userapp.dto.EmployeeDTO;
import com.example.userapp.model.Employee;
/**
 * MapStruct mapper for employee (entity <-> DTO)
 * @author idris
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
	public EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
	/**
	 * Map employee to a EmployeeDTO
	 * @param employee
	 * @return employeeDTO type {@link com.example.userapp.dto.EmployeeDTO EmployeeDTO}
	 */
	EmployeeDTO entityToDTO(Employee employee);
	/**
	 * Map employeeDTO to a User
	 * @param employeeDTO
	 * @return employee type {@link com.example.userapp.model.Employee Employee}
	 */
	@Mapping(target = "id", ignore = true)
	Employee dtoToEntity(EmployeeDTO employeeDTO);
}
