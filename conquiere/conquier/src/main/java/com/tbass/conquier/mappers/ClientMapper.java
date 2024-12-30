package com.tbass.conquier.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.tbass.conquier.dtos.ClientDto;
import com.tbass.conquier.entity.Client;

@Mapper
@Component
public interface ClientMapper {

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "surname", source = "entity.surname")
	@Mapping(target = "birthdate", source = "entity.birthdate", dateFormat = "dd-MM-yyyy")
	ClientDto toDto(Client entity);

	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "surname", source = "dto.surname")
	@Mapping(target = "birthdate", source = "dto.birthdate", dateFormat = "dd-MM-yyyy")
	Client toEntity(ClientDto dto);

}
