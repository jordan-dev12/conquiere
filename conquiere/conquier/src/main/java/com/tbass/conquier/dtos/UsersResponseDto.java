package com.tbass.conquier.dtos;

import java.util.List;

import com.tbass.conquier.entity.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modèle liste utilisateurs")
public class UsersResponseDto {

	@Schema(description = "Liste utilisateur")
	List<UserEntity> clients;

	@Schema(description = "Nombres totals d'utilisateurs retournées")
	int totals;

}
