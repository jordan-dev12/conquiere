package com.tbass.conquier.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {

	@Min(0)
	private Integer page = 0;

	@Min(0)
	@Max(50)
	private Integer size = 10;

}
