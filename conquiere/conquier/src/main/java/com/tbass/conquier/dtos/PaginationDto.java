package com.tbass.conquier.dtos;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {

	@Positive
    private Integer page = 1;

	@Positive
    private Integer size = 10;


}
