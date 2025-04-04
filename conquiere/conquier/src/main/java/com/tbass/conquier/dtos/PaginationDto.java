package com.tbass.conquier.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modèle de pagination")
public class PaginationDto {

	@Min(0)
	@Schema(description = "Numéro de la page courante (commence à 0)", example = "0")
	private Integer page = 0;

	@Min(1)
	@Max(50)
	@Schema(description = "Taille de la page", example = "20")
	private Integer size = 10;

}
