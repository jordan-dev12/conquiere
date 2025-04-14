package com.tbass.conquier.utility;

import org.springframework.data.domain.PageRequest;

import com.tbass.conquier.dtos.PaginationDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

	public static PageRequest getPageable(PaginationDto request) {
		return PageRequest.of(request.page(), request.size());
	}

}
