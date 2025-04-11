package com.tbass.conquier.service;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserRegistrationRequestDto;
import com.tbass.conquier.dtos.UserRegistrationResponseDto;
import com.tbass.conquier.dtos.UsersResponseDto;

public interface UserService {

	public UserRegistrationResponseDto registerUsers(UserRegistrationRequestDto user);

	public UserRegistrationResponseDto update(UserRegistrationRequestDto user);

	public void delete(long id);

	public UserRegistrationResponseDto getById(long id);

	public UserRegistrationResponseDto getByUsername(String username);

	public UsersResponseDto getClients(PaginationDto pagination);

	public void deleteAll();

}
