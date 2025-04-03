package com.tbass.conquier.service;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserDto;
import com.tbass.conquier.dtos.UsersResponseDto;

public interface UserService {

	public UserDto save(UserDto user);

	public UserDto update(UserDto user);

	public void delete(long id);

	public UserDto getById(long id);

	public UsersResponseDto getClients(PaginationDto pagination);

}
