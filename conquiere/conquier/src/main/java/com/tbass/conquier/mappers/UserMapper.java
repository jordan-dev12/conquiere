package com.tbass.conquier.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.tbass.conquier.dtos.UserRegistrationRequestDto;
import com.tbass.conquier.dtos.UserRegistrationResponseDto;
import com.tbass.conquier.entity.UserEntity;

@Mapper
@Component
public interface UserMapper {

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "surname", source = "entity.surname")
	@Mapping(target = "email", source = "entity.email")
	@Mapping(target = "birthdate", source = "entity.birthdate", dateFormat = "dd-MM-yyyy")
	@Mapping(target = "roles", source = "entity.roles")
	UserRegistrationResponseDto toDto(UserEntity entity);

	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "surname", source = "dto.surname")
	@Mapping(target = "email", source = "dto.email")
	@Mapping(target = "password", source = "dto.password")
	@Mapping(target = "birthdate", source = "dto.birthdate", dateFormat = "dd-MM-yyyy")
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "id", ignore = true)
	UserEntity toEntity(UserRegistrationRequestDto dto);

}
