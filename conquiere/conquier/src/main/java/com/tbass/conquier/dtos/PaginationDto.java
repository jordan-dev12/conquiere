package com.tbass.conquier.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Schema(description = "Modèle de pagination")
@Builder(toBuilder = true)
public record PaginationDto(@Min(0) Integer page, @Min(1) @Max(50) Integer size) {

}
