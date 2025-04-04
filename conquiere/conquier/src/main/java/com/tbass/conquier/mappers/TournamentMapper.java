package com.tbass.conquier.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.tbass.conquier.dtos.TournamentRequestDto;
import com.tbass.conquier.dtos.TournamentResponseDto;
import com.tbass.conquier.entity.TournamentEntity;

@Mapper
@Component
public interface TournamentMapper {

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "description", source = "entity.description")
	@Mapping(target = "dateIssued", source = "entity.dateIssued")
	@Mapping(target = "eventDate", source = "entity.eventDate")
	@Mapping(target = "adminId", ignore = true)
	TournamentResponseDto toDto(TournamentEntity entity);

	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "dateIssued", source = "dto.dateIssued")
	@Mapping(target = "eventDate", source = "dto.eventDate")
	@Mapping(target = "creator", ignore = true)
	@Mapping(target = "participants", ignore = true)
	@Mapping(target = "id", ignore = true)
	TournamentEntity toEntity(TournamentRequestDto dto);

}
