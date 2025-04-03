package com.tbass.conquier.service;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserRegistrationRequestDto;
import com.tbass.conquier.dtos.UserRegistrationResponseDto;
import com.tbass.conquier.dtos.UsersResponseDto;

public interface UserService {

	public UserRegistrationResponseDto save(UserRegistrationRequestDto user);

	public UserRegistrationResponseDto update(UserRegistrationRequestDto user);

	public void delete(long id);

	public UserRegistrationResponseDto getById(long id);

	public UsersResponseDto getClients(PaginationDto pagination);

}
