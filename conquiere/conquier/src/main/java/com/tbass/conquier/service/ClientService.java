package com.tbass.conquier.service;

import com.tbass.conquier.dtos.ClientDto;
import com.tbass.conquier.dtos.ClientsResponseDto;
import com.tbass.conquier.dtos.PaginationDto;

public interface ClientService {

	public ClientDto save(ClientDto client);

	public ClientDto update(ClientDto client);

	public void delete(long id);

	public ClientDto getById(long id);

	public ClientsResponseDto getClients(PaginationDto pagination);

}
