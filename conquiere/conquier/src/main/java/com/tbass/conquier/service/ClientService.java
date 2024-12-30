package com.tbass.conquier.service;

import java.util.List;

import com.tbass.conquier.dtos.ClientDto;

public interface ClientService {

	public ClientDto save(ClientDto todo);

	public ClientDto update(ClientDto todo);

	public void delete(long id);

	public ClientDto getById(long id);

	public List<ClientDto> getAllTodo();

}
